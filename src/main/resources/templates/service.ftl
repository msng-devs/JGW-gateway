<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <title>Gateway-Storm services</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-dark-5@1.1.3/dist/css/bootstrap-night.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <nav class="navbar navbar-light bg-light p-3 m-3">
          <form class="form-inline">
            <button class="btn btn-outline-secondary" type="button" id="btn-logout">logout</button>
            <button class="btn btn-outline-success" type="button" id="btn-refresh">Refresh Gateway</button>
          </form>
    </nav>
</head>
<body>

    <div class="container-fluid m-5 p-5">
        <div class="row">
            <div class="col-md-12 text-center">
                <div class="jumbotron m-5 py-3">
                    <h2 class="my-3">
                        <i class="fa-solid fa-bars-staggered"></i> 서비스 목록
                    </h2>

                    <p class="my-3">
                        현재 Gateway에 등록된 서비스들입니다.
                    </p>
                    <p class="text-muted">
                        <i class="fa-solid fa-question"></i> 사용 가이드
                    </p>
                </div>
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#add-service">
                                <i class="fa-solid fa-plus"></i> 신규 추가
                    </button>
                </div>
                <div class="row m-5 p-2">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">
                        <table class="table">
                          <thead>
                            <tr>
                              <th scope="col">서비스 명</th>
                              <th scope="col">서비스 설명</th>
                              <th scope="col">도메인</th>
                              <th scope="col">수정</th>
                              <th scope="col">path 관리</th>
                            </tr>
                          </thead>
                          <tbody>

                            <#list services as service>
                            <tr id="${service.getId()}">
                              <th scope="row">${service.getName()}</th>
                              <td>${service.getIndex()}</td>
                              <td>${service.getDomain()}</td>
                              <td><a class="icon-block"><i class="fa-solid fa-pen-to-square" onclick="service_update_modal('${service.id}')"></i></a></td>
                              <td><a href="/path/${service.getId()}" class="icon-block"><i class="fa-sharp fa-solid fa-gear"></i></a></td>
                            </tr>
                            </#list>
                          </tbody>
                        </table>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    add service -->
    <div class="modal fade" id="add-service" tabindex="-1" aria-labelledby="ModalFormLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">신규 Service</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
                </div>
                <div class="modal-body">
                    <form>

                        <div class="form-group">

                            <label class="my-3" for="add-service-name">서비스 명</label>
                            <input class="form-control" id="add-service-name">

                            <label class="my-3" for="add-service-index">서비스 설명</label>
                            <input class="form-control" id="add-service-index">

                            <label class="my-3" for="add-service-domain">도메인</label>
                            <input class="form-control" id="add-service-domain">
                        </div>
                        <hr class="my-3">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-outline-success" id="btn-service-add">등록</button>
                </div>
            </div>
        </div>
    </div>
    <!--    update service -->
    <div class="modal fade" id="update-service" tabindex="-1" aria-labelledby="ModalFormLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Service 정보 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
                </div>
                <div class="modal-body">
                    <form>

                        <div class="form-group">

                            <label class="my-3" for="update-service-name">서비스 명</label>
                            <input class="form-control" id="update-service-name">

                            <label class="my-3" for="update-service-index">서비스 설명</label>
                            <input class="form-control" id="update-service-index">

                            <label class="my-3" for="update-service-domain">도메인</label>
                            <input class="form-control" id="update-service-domain">
                            <a id="update-service-id" style="display: none"></a>
                        </div>
                        <hr class="my-3">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-outline-success" id="btn-service-update">등록</button>
                    <button type="button" class="btn btn-outline-danger" id="btn-service-check-rm">삭제</button>
                </div>
            </div>
        </div>
    </div>
    <!--    delete service -->
    <div class="modal fade" id="delete-service" tabindex="-1" aria-labelledby="ModalFormLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">정말로 삭제 하시겠습니까?</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
                </div>
                <div class="modal-body">
                    <a id="rm-service-index">삭제후 복구가 불가능합니다!</a>
                    <a id="rm-service-id" style="display: none"></a>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-outline-danger" id="btn-service-rm">삭제</button>
                </div>
            </div>
        </div>
    </div>
    <!-- gateway refresh -->
    <div class="modal fade" id="gateway-refresh-modal" tabindex="-1" aria-labelledby="ModalFormLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Gateway refresh</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <a>경고! 해당 기능을 사용하면, 일시적으로 gateway 서비스가 중단됩니다! (즉 일시적으로 모든 서비스가 중단됩니다.) test 환경이 아니라면, 주의하여 사용하세요.</a>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal" id="btn-gateway-refresh">진행하기</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src ="/js/gateway-storm-main.js"></script>
</body>
</html>