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

    // AJAX FUNCTIONS
    var ifError = function(e){
        var errors = JSON.parse(e.responseText);

        $.each(errors, function(e, i){

            var $el = $('input[name='+e+']');

            if(!$el.hasClass("errorInput"))
                $('input[name='+e+']').after("<div class='error'>"+i+"</div>").addClass("errorInput");

            HAS_ERRORS = true;
        });

      

        changeButtonState();
    };

    var ifSuccess = function(e){
        HAS_ERRORS = false;

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