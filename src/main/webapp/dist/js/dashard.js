$(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $("#metrics_wrapper"); //Fields wrapper
    var add_button      = $("#metrics_add_button"); //Add button ID
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            $(wrapper).append('<div><input name="metrics[' + (x-1) + '].code"> <input name="metrics[' + (x-1) + '].name"><button class="remove_field">Remove</button></div>'); //add input box
            x++; //text box increment
        }
    });
    
    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
        e.preventDefault(); $(this).parent('div').remove(); x--;
    })
});

$(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $("#switches_wrapper"); //Fields wrapper
    var add_button      = $("#switches_add_button"); //Add button ID
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            $(wrapper).append('<input name="switches[' + (x-1) + '].name"> <input name="switches[' + (x-1) + '].description"> <input name="switches[' + (x-1) + '].pin"><button class="remove_field">Remove</button></div>'); //add input box
            x++; //text box increment
        }
    });
    
    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
        e.preventDefault(); $(this).parent('div').remove(); x--;
    })
});

