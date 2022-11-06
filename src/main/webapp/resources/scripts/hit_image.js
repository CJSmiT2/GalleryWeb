$( document ).ready(function() {
    $.ajax({
            type: 'POST',
            url: '/hit_image/' + $('#imageAlias').val(),
            async: true,
            data: 'beep',
            complete: function () {
            },
            success: function (response) {
            },
            error: function () {
            }
        });
});