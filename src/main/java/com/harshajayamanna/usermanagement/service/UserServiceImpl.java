package com.harshajayamanna.usermanagement.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.Sha256Hash;

import com.harshajayamanna.usermanagement.entity.Group;
import com.harshajayamanna.usermanagement.entity.User;
import com.harshajayamanna.usermanagement.entity.UserActivation;

@Stateless
public class UserServiceImpl implements UserService, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "recruitDBPersistenceUnit")
	private EntityManager em;

	@Inject
	private MailingService mailService;

	@Inject
	private GroupService groupService;

	public UserServiceImpl() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public void addUser(String email, String password, String firstName, String lastName)
			throws MessagingException {

		password = new Sha256Hash(password).toString();

		User newUser = new User(email, password, firstName, lastName, false);

		Group group = groupService.getByName("USERS");

		newUser.addGroup(group);

		em.persist(newUser);

		initiateUserActivation(email);

	}

	private void initiateUserActivation(String email) throws MessagingException {

		Query userQuery = em.createQuery("SELECT u FROM User u WHERE u.email=:email");
		userQuery.setParameter("email", email);

		User user = (User) userQuery.getSingleResult();

		UserActivation userActivation = new UserActivation(user);

		em.persist(userActivation);

		Query userActivationQuery = em.createQuery("SELECT u FROM UserActivation u WHERE u.user.id=:userId");
		userActivationQuery.setParameter("userId", user.getId());

		UserActivation savedUserActivation = (UserActivation) userActivationQuery.getSingleResult();

		// sendEmail(email, "RecruitTimes Account Activation",
		// "user-activation.xhtml",savedUserActivation.getId());

	}

	private void sendEmail(String email, String subject, String link, String key) throws MessagingException {

		String message = "<h2>RecruitTimes Account Activation</h2>" + "<p>Hi there!</p>"
				+ "<p>Plese click the belowe link to activate your account.<p>"
				+ "<p><a href='http://localhost:9080/includes/user/" + link + "?key=" + key + "'>Activate</a></p>"
				+ "<p>Thanks.</p>";

		mailService.sendSimpleMail(email, subject, message);

	}

	@Override
	public boolean isEmailExists(String email) {

		Query userQuery = em.createQuery("SELECT u FROM User u WHERE u.email=:email");
		userQuery.setParameter("email", email);

		try {
			userQuery.getSingleResult();
		} catch (NoResultException e) {
			return true;
		}

		return false;
	}

	@Override
	public boolean activateUser(String key) {

		Query userActivationQuery = em.createQuery("SELECT u FROM UserActivation u WHERE u.id=:id");
		userActivationQuery.setParameter("id", key);

		UserActivation userActivation = null;
		try {
			userActivation = (UserActivation) userActivationQuery.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}

		User user = userActivation.getUser();

		Query userUpdateQuery = em.createQuery("UPDATE User set active=true WHERE u.id=:id");
		userUpdateQuery.setParameter("id", user.getId());

		userUpdateQuery.executeUpdate();

		em.remove(userActivation);

		return true;
	}

	@Override
	public void sendPasswordResetLink(String email) throws MessagingException {

		Query userQuery = em.createQuery("SELECT u FROM User u WHERE u.email=:email");
		userQuery.setParameter("email", email);
		User user = (User) userQuery.getSingleResult();

		deleteOldToken(user.getId());

		insertNewToken(user.getId(), user.getEmail());

	}

	private void insertNewToken(long userId, String email) throws MessagingException {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String todayAsString = now.format(formatter);

		String key = UUID.randomUUID().toString();

		Query pwResetTokenInsertQuery = em.createNativeQuery(
				"INSERT INTO passsord_reset_token VALUES ('" + key + "'," + userId + ",'" + todayAsString + "')");

		pwResetTokenInsertQuery.executeUpdate();

		sendEmail(email, "RecruitTimes Password Reset", "validate-password-reset.xhtml", key);

	}

	private void deleteOldToken(long userId) {

		Query pwResetTokenSelectQuery = em
				.createNativeQuery("SELECT EXISTS(SELECT * FROM passsord_reset_token WHERE USER_ID=" + userId + ")");

		if (pwResetTokenSelectQuery.getSingleResult().toString().equals("1")) {
			Query pwResetTokenDeleteQuery = em
					.createNativeQuery("DELETE FROM passsord_reset_token WHERE USER_ID=" + userId + "");
			pwResetTokenDeleteQuery.executeUpdate();
		}

	}

	@Override
	public boolean validatePasswordResetKey(String key) {

		Query pwResetTokenSelectQuery = em
				.createNativeQuery("SELECT CREATED_DATE FROM passsord_reset_token WHERE ID='" + key + "'");

		try {

			Timestamp d = (Timestamp) pwResetTokenSelectQuery.getSingleResult();

			if (isKeyAgeValid(d)) {
				return true;
			}

		} catch (NoResultException e) {
			return false;
		}

		return false;
	}

	private boolean isKeyAgeValid(Timestamp d) {

		LocalDateTime time = d.toLocalDateTime();

		long minutes = time.until(LocalDateTime.now(), ChronoUnit.MINUTES);

		if (minutes > 20) {
			return false;
		}

		return true;
	}

	@Override
	public void resetPassword(String key, String newPassword) {
		Query pwUpdateQuery = em.createNativeQuery("UPDATE USER SET PASSWORD='" + newPassword
				+ "' WHERE ID IN ( SELECT USER_ID FROM passsord_reset_token WHERE ID='" + key + "' )");

		pwUpdateQuery.executeUpdate();

		Query pwResetTokenDeleteQuery = em.createNativeQuery("DELETE FROM passsord_reset_token WHERE ID='" + key + "'");
		pwResetTokenDeleteQuery.executeUpdate();

	}

	@Override
	public User getUserByEmail(String email) {

		Query userQuery = em.createQuery("SELECT u FROM User u WHERE u.email=:email");
		userQuery.setParameter("email", email);
		User user = (User) userQuery.getSingleResult();
		return user;

	}

}
