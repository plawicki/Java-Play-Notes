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

        var $inputes = $.merge($('input'), $('textarea'));
        $inputes = $.merge($inputes, $('select'));

        $.each($inputes, function(i, el){

            $(el).change(function(e){
                $('[name='+e.target.id+']').val($(this).val());
            });
        });
    }();

    var removeError = function(elName){
        $input = $('[name='+elName+']');
        $input.removeClass("errorInput");
        $input.parent().children('.error').remove();
    }

    var addError = function(elName, error){
        $('[name='+elName+']').after("<div class='error'>"+error+"</div>").addClass("errorInput");
    }

    // AJAX FUNCTIONS
    var ifError = function(e){

        var errors = JSON.parse(e.responseText);

        $.each(errors, function(name, text){

            var $el = $('[name='+name+']');

            if(!$el.hasClass("errorInput"))
                addError(name, text);

            HAS_ERRORS = true;

        });

        $.each($('[name]'), function(i, el){

            var $elName = $(el).attr("name");

            if(!errors[$elName])
                removeError($elName);

        });

        changeButtonState();
    };

    var ifSuccess = function(e){
        HAS_ERRORS = false;

       $.each($('[name]'), function(i, el){

            var $elName = $(el).attr("name");

            removeError($elName);
        });

        changeButtonState();
    }

    // EVENTS
    $('[name]').focusout(function(e){

        $.ajax({
            method: "POST",
            data: $('form[action="/send/"]').serialize(),
            url: "/validate/",
            error: ifError,
            success: ifSuccess
        });
    });
});