var main = {
    init : function () {
        var _this = this;
        $('#btn-service-update').on('click', function () {
            _this.service_update();
        });
        $('#btn-service-add').on('click', function () {
            _this.service_add();
        });
        $('#btn-service-check-rm').on('click', function () {
            _this.service_rm_check();
        });
        $('#btn-service-rm').on('click', function () {
            _this.service_rm();
        });
        $('#btn-path-update').on('click', function () {
            _this.path_update();
        });
        $('#btn-path-add').on('click', function () {
            _this.path_add();
        });
        $('#btn-path-check-rm').on('click', function () {
            _this.path_rm_check();
        });
        $('#btn-path-rm').on('click', function () {
            _this.path_rm();
        });
        $('#btn-login').on('click', function () {
            _this.get_login();
        });
        $('#btn-logout').on('click', function () {
            _this.get_logout();
        });
        $('#btn-refresh').on('click', function () {
            _this.gateway_refresh();
        });
        $('#btn-gateway-refresh').on('click', function () {
            _this.firebase_login();
        });

    },
    gateway_refresh : function (){
        $('#gateway-refresh-modal').modal('show');
    },

    firebase_login : function () {
        var data = {
            email : $("#gateway-refresh-id").val(),
            password : $("#gateway-refresh-pw").val(),
            returnSecureToken: true
        };
        $.ajax({
            type: 'POST',
            url: "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+$("#firebase-key").text(),
            contentType:'application/json',
            data: JSON.stringify(data)
        }).done(function(data) {

            var token = data.idToken
            console.log(token)
            start_refresh(token)
        }).fail(function (error) {
            console.log(error)
            alert("email이나 비밀번호가 잘못되었습니다.");
        });
    },

    get_login : function () {
        var data = {
            pw : $("#pw").val(),
            id : $("#id").val()
        };

        $.ajax({
            type: 'POST',
            beforeSend: function(xhr){
                xhr.withCredentials = true;
            },
            url: "/api/v1/auth",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('로그인에 성공했습니다!');
            location.replace("/");
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    get_logout : function () {
        $.ajax({
            type: 'DELETE',
            beforeSend: function(xhr){
                xhr.withCredentials = true;
            },
            url: "/api/v1/auth",
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('로그아웃했습니다.');
            location.replace("/login");
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    service_update : function () {
        var data = {
            name : $("#update-service-name").val(),
            index : $("#update-service-index").val(),
            domain : $("#update-service-domain").val(),
        };

        $.ajax({
            type: 'PUT',
            url: "/api/v1/service/" + $("#update-service-id").text(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('서비스 정보가 업데이트 되었습니다!');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    service_add : function () {
        var data = {
            name : $("#add-service-name").val(),
            index : $("#add-service-index").val(),
            domain : $("#add-service-domain").val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/v1/service/",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('성공적으로 서비스가 추가되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    service_rm_check: function (){
        $('#update-service').modal('hide');
        $('#delete-service').modal('show');
        $("#rm-service-id").text($("#update-service-id").text());
        $("#rm-service-index").text($("#update-service-name").text() + " 해당 서비스를 삭제하면, 하위 path도 모두 함께 삭제되며, 다시는 복구할 수 없습니다. 진행하시겠습니까?");

        },
    service_rm : function () {

        $.ajax({
            type: 'DELETE',
            url: "/api/v1/service/" + $("#rm-service-id").text(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('성공적으로 삭제되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    path_update : function () {
        var data = {
            path : $("#update-path-path").val(),
            method_id : parseInt($("#update-path-method").find(":selected").val()),
            role_id :  parseInt($("#update-path-role").find(":selected").val()),
            option : $("#update-path-option").find(":selected").val()
        };

        $.ajax({
            type: 'PUT',
            url: "/api/v1/path/" + $("#update-path-id").text(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('Path 정보가 업데이트 되었습니다!');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    path_add : function () {
        var data = {
            path : $("#add-path-path").val(),
            method_id : parseInt($("#add-path-method").find(":selected").val()),
            role_id : parseInt($("#add-path-role").find(":selected").val()),
            service_id: parseInt($("#service-id").text()),
            option : $("#add-path-option").find(":selected").val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/v1/path/",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('성공적으로 Path가 추가되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    path_rm_check: function (){
        $('#update-path').modal('hide');
        $('#delete-path').modal('show');
        $("#rm-path-id").text($("#update-path-id").text());

        },
    path_rm : function () {

        $.ajax({
            type: 'DELETE',
            url: "/api/v1/path/" + $("#rm-path-id").text(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('성공적으로 삭제되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();

jQuery('#add-path-option').change(function() {
	var state = jQuery('#add-path-option').val();
	if ( state === 'RBAC' ) {
		jQuery('#add-path-role').show();
        jQuery('#add-path-role-label').show();
	} else {
		jQuery('#add-path-role').hide();
        jQuery('#add-path-role-label').hide();
	}
    });

function service_update_modal(tr_id){

    var td = $('#'+tr_id).closest('tr').children();

    var service_name = td.eq(0).text()
    var service_index = td.eq(1).text()
    var service_domain = td.eq(2).text()
    console.log(service_name,service_index,service_domain)
    $('#update-service').modal('show');
    $("#update-service-id").text(tr_id);
    $("#update-service-name").val(service_name)
    $("#update-service-index").val(service_index)
    $("#update-service-domain").val(service_domain)

}

function path_update_modal(tr_id){
    var td = $('#'+tr_id).closest('tr').children();

    var method_name = td.eq(0).text()
    var path = td.eq(1).text()
    var option_name = td.eq(2).text()
    var role_name = td.eq(3).text()
    console.log(method_name,path,option_name,role_name)
    $('#update-path').modal('show');
    $('#update-path-id').text(tr_id)
    $('#update-path-method option:contains('+ method_name +')').attr('selected', true);
    $("#update-path-path").val(path)
    $('#update-path-option option:contains('+ option_name +')').attr('selected', true);
    if (option_name === "RBAC"){
        $('#update-path-role option:contains('+ role_name +')').attr('selected', true);
        $("#update-path-role").show()
        $("#update-path-role-label").show()
    }
}
function start_refresh(token){

        $.ajax({
            type: 'GET',
            url: $("#gateway-path").text(),
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization","bearer " + token);
            }
        }).done(function(data) {
            alert("성공적으로 refresh 되었습니다!");
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
}