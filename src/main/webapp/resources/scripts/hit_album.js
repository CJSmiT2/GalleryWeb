$( document ).ready(function() {
    $.ajax({
            type: 'POST',
            url: '/hit_album/' + $('#albumAlias').val(),
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