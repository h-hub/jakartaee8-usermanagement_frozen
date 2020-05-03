function handleDisableButton(data) {
    if (data.source.type != "submit") {
        return;
    }

    switch (data.status) {
        case "begin":
            data.source.disabled = true;
            $( ".loading-img" ).show();
            break;
        case "complete":
            data.source.disabled = false;
            $( ".loading-img" ).hide();
            break;
    }    
}

jsf.ajax.addOnEvent(handleDisableButton);

function handleDisableLoginButton(data) {
    if (data.source.type != "submit") {
        return;
    }

    switch (data.status) {
        case "begin":
            data.source.disabled = true;
            break;
        case "complete":
            data.source.disabled = false;
            break;
    }    
}

jsf.ajax.addOnEvent(handleDisableLoginButton);