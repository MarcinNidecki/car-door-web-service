$.getJSON("https://sleepy-oasis-69014.herokuapp.com/v1/city/getCities", function (json) {
    $('#myselect').empty();
    $('#myselect').append($('<option>').text("Select"));
    $.each(json, function (i, obj) {
        $('#myselect').append($('<option>').text(obj.name).attr('value', obj.id));
    });
});