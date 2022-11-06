var tickCount = 0;
$(function () {
    setInterval('addSecondsViewTime()', 5000);
});
function addSecondsViewTime() {
    if (tickCount != 0 && tickCount <= 12) {
        $.ajax({
            type: 'POST',
            url: '/add_seconds_view_time/' + $('#imageAlias').val(),
            async: true,
            data: 'beep',
            complete: function () {
            },
            success: function (response) {
            },
            error: function () {
            }
        });
    }
    tickCount++;
}