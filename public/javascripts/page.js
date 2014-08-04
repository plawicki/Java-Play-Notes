$(function(){

    var HAS_ERRORS = false;


    var cloningForm = function(){
        var form = $('form[action="/send/"').clone();
        $('div.col-md-12').append(form);
    }();

    var changeButtonState = function(){
        if(HAS_ERRORS)
            $('input[type="submit"]').attr('disabled','disabled');
        else
        {
            $('input[type="submit"]').removeAttr('disabled');
        }
    }

    var cloningInputs = function(){
        $.each($('input'), function(i, el){

            $(el).keyup(function(e){
                $('input[name='+e.target.id+']').val($(this).val());
            });
        });
    }();

    var removeError = function(elName){
        $input = $('input[name='+elName+']');
        $input.removeClass("errorInput");
        $input.parent().children('.error').remove();
    }

    var addError = function(elName, error){
        $('input[name='+elName+']').after("<div class='error'>"+error+"</div>").addClass("errorInput");
    }

    // AJAX FUNCTIONS
    var ifError = function(e){
        var errors = JSON.parse(e.responseText);

        $.each(errors, function(name, text){

            var $el = $('input[name='+name+']');

            if(!$el.hasClass("errorInput"))
                addError(name, text);

            HAS_ERRORS = true;

        });

        $.each($('input'), function(i, el){

            var $elName = $(el).attr("name");

            if(!errors[$elName])
                removeError($elName);

        });

        changeButtonState();
    };

    var ifSuccess = function(e){
        HAS_ERRORS = false;

       $.each($('input'), function(i, el){

            var $elName = $(el).attr("name");

            removeError($elName);
        });

        changeButtonState();
    }

    // EVENTS
    $('input[type="text"]').focusout(function(e){

        $.ajax({
            method: "POST",
            data: $('form[action="/send/').serialize(),
            url: "/validate/",
            error: ifError,
            success: ifSuccess
        });
    });
});