-- 스키마 생성
CREATE SCHEMA S_AI;
CREATE SCHEMA S_COMPANY_PRODUCT;
CREATE SCHEMA S_HUB;
CREATE SCHEMA S_ORDER_DELIVERY;
CREATE SCHEMA S_SLACK;
CREATE SCHEMA S_USER;


-- 사용자 생성
CREATE USER AIU WITH PASSWORD '1234';
CREATE USER CPR WITH PASSWORD '1234';
CREATE USER HUB WITH PASSWORD '1234';
CREATE USER ODL WITH PASSWORD '1234';
CREATE USER SLC  WITH PASSWORD '1234';
CREATE USER USR WITH PASSWORD '1234';

-- 권한 부여
GRANT ALL PRIVILEGES ON SCHEMA S_AI TO AIU ;
GRANT ALL PRIVILEGES ON SCHEMA S_COMPANY_PRODUCT TO CPR ;
GRANT ALL PRIVILEGES ON SCHEMA S_HUB TO HUB;
GRANT ALL PRIVILEGES ON SCHEMA S_ORDER_DELIVERY TO ODL ;
GRANT ALL PRIVILEGES ON SCHEMA S_SLACK TO SLC  ;
GRANT ALL PRIVILEGES ON SCHEMA S_USER TO USR ;

CREATE TABLE S_HUB.p_hub_path
(
    hub_path_id UUID PRIMARY KEY,
    created_at  TIMESTAMP,
    created_by  UUID,
    deleted_at  TIMESTAMP,
    deleted_by  UUID,
    is_deleted  BOOLEAN,
    updated_at  TIMESTAMP,
    updated_by  UUID,
    distance    INT,
    duration    INT,
    from_hub_id UUID,
    to_hub_id   UUID
);


CREATE TABLE S_HUB.p_hub
(
    hub_id     UUID PRIMARY KEY,
    hub_name   VARCHAR(255) NOT NULL,
    address    VARCHAR(255) NOT NULL,
    latitude   VARCHAR(255) NOT NULL,
    longitude  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP,
    deleted_by UUID,
    is_deleted BOOLEAN
);

INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('2d92d971-b752-48b8-a394-7a9f47540c4f', null, null, null, null, false, null, null, '서울시 구로구 오류동 123',
        '37.4908771316435', '126.835143023115', '구로 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('a786b40d-6cbb-45c1-9182-f6cb67ae2783', null, null, null, null, false, null, null, '서울시 강서구 가양동 449-1',
        '37.5595425292609', '126.859606347136', '강서 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('9dbb5a86-1216-4564-b34f-e7d255c2eb50', null, null, null, null, false, null, null, '서울시 영등포구 문래북로 81',
        '37.5206771526839', '126.894327974004', '영등포 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('ccab9b17-788d-402c-9945-050c4cb68004', null, null, null, null, false, null, null, '서울시 도봉구 도봉동 63',
        '37.6833841238602', '127.048330017205', '도봉 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('0062c835-6d53-4c07-9b7b-8dafabd04baa', null, null, null, null, false, null, null, '경기도 김포 월곶면 군하리 341-17',
        '37.7089679471972', '126.554673773887', '김포 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('6e70e165-cf6c-41c4-a540-aa9506573761', null, null, null, null, false, null, null, '경기도 고양시 일산 동구 성석동 733-2',
        '37.695169066381', '126.797063271445', '일산 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('1b053d4a-3430-47aa-baf5-b63780b71bc8', null, null, null, null, false, null, null, '경기도 시흥시 정왕동 1359-4',
        '37.3345164839419', '126.714671852921', '시흥 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('5ab105ae-fe3a-4ec8-971c-97e06508e6d6', null, null, null, null, false, null, null, '경기도 구리시 인창동 31-3',
        '37.6172296195404', '127.138715602091', '구리 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('831ca0f4-1393-425b-9b30-0bd60bd348a7', null, null, null, null, false, null, null, '경기도 안양시 동안구 호계동 907-5',
        '37.3857124041968', '126.94527825538', '안양 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('99e00940-9bfe-4961-b6a1-57c6bc6d102f', null, null, null, null, false, null, null, '경기도 수원시 권선구 입북동 235-1',
        '37.2936835593183', '126.961186520061', '수원 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('5b0981ca-f393-4617-bcb6-84d0e2d06c3d', null, null, null, null, false, null, null, '인천광역시 부평구 삼산동 106-36',
        '37.516758482149', '126.751472389113', '인천 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('87837776-b83f-436c-a79d-1c1032e60383', null, null, null, null, false, null, null, '경상남도 김해시 어방동 1066-19',
        '35.2300446599993', '128.907113109729', '김해 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('c94e1e2b-1658-4a54-b705-6123c1fef219', null, null, null, null, false, null, null, '경상남도 창원시 의창구 차룡로 48번길 49',
        '35.2414187313228', '128.633146572352', '창원 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('c04f4f3e-356f-4f06-a512-87abf6034839', null, null, null, null, false, null, null, '울산광역시 남구 매암동 204',
        '35.507247497932', '129.380895143878', '울산 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('04ec85ff-c243-47d7-b7ed-1e656c94bb7d', null, null, null, null, false, null, null, '부산 사상구 감전동 148-5',
        '35.1543055217735', '128.974507193757', '부산 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('a1115838-0026-42c4-a691-a4f9fb97c3d2', null, null, null, null, false, null, null, '경상북도 경산시 남산면 인흥리 215-10',
        '35.805836871545', '128.797278034992', '경산 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('9209b67e-0829-4f92-9353-b408b2948568', null, null, null, null, false, null, null, '경상북도 구미시 광평동 423-4',
        '36.1083306762043', '128.358868891811', '구미 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('fea57c6b-4b6f-43bb-95ef-5100ca2e47c8', null, null, null, null, false, null, null, '대구 달서구 장기동 836-5',
        '35.8445640005131', '128.524150897156', '대구 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('0d3df28e-d51c-4e8f-a36e-ae7b8d944c13', null, null, null, null, false, null, null, '대전광역시 대덕구 대화동 262-1',
        '36.3650509740843', '127.409050064032', '대전 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('02e65e5e-7a8a-49f0-b5e8-8277f399e797', null, null, null, null, false, null, null, '충청남도 공주시 반포면 송곡리 149-6',
        '36.4135551766255', '127.271554508644', '공주 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('95d51306-700b-47bc-932b-2f353099944a', null, null, null, null, false, null, null, '충청북도 청주시 흥덕구 강내면 사인리 313-1',
        '36.6286650603181', '127.373053595122', '청주 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('bd801f36-6151-4531-a391-c9f0e740763e', null, null, null, null, false, null, null, '광주광역시 남구 송하동 254-13',
        '35.1114797171399', '126.880816933627', '전라광주 센터');
INSERT INTO S_HUB.p_hub (hub_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at, updated_by,
                         address, latitude, longitude, hub_name)
VALUES ('048ce871-1386-45cf-a5f4-0a9fea0970a7', null, null, null, null, false, null, null, '전북 전주시 덕진구 여의동 739-1',
        '35.8627522272185', '127.078147431603', '전주 센터');



INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('31a82c67-ae45-4daf-8b9b-045545326bb9', null, null, null, null, false, null, null, 31585, 2498,
        '99e00940-9bfe-4961-b6a1-57c6bc6d102f', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('10a89576-7015-4dda-83b3-632defa77873', null, null, null, null, false, null, null, 36686, 2458,
        '2d92d971-b752-48b8-a394-7a9f47540c4f', '99e00940-9bfe-4961-b6a1-57c6bc6d102f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('414b446b-4e0f-44a3-8482-d49bbebb4838', null, null, null, null, false, null, null, 11692, 1804,
        '2d92d971-b752-48b8-a394-7a9f47540c4f', 'a786b40d-6cbb-45c1-9182-f6cb67ae2783');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('d95da144-1420-427e-9dc5-29d5547cf250', null, null, null, null, false, null, null, 11214, 1896,
        'a786b40d-6cbb-45c1-9182-f6cb67ae2783', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('ab7e6853-9ec9-44aa-bf61-fc2439e6d38c', null, null, null, null, false, null, null, 33616, 2215,
        'a786b40d-6cbb-45c1-9182-f6cb67ae2783', '0062c835-6d53-4c07-9b7b-8dafabd04baa');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('3f3dafee-321e-45b6-98a1-3ea2d4720d95', null, null, null, null, false, null, null, 34551, 2682,
        '0062c835-6d53-4c07-9b7b-8dafabd04baa', 'a786b40d-6cbb-45c1-9182-f6cb67ae2783');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('05a7a8d1-f992-473e-a4bd-7803c207162a', null, null, null, null, false, null, null, 7319, 1022,
        '9dbb5a86-1216-4564-b34f-e7d255c2eb50', 'a786b40d-6cbb-45c1-9182-f6cb67ae2783');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('c76d04a0-50bb-4325-ae70-2f4ffddc14da', null, null, null, null, false, null, null, 7015, 1148,
        'a786b40d-6cbb-45c1-9182-f6cb67ae2783', '9dbb5a86-1216-4564-b34f-e7d255c2eb50');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('ae650f58-a505-4b65-8a12-6afc71a25460', null, null, null, null, false, null, null, 22012, 1794,
        'a786b40d-6cbb-45c1-9182-f6cb67ae2783', '6e70e165-cf6c-41c4-a540-aa9506573761');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('161b806a-dea8-4fe7-bd01-dbf7c41e95ae', null, null, null, null, false, null, null, 27469, 2000,
        '6e70e165-cf6c-41c4-a540-aa9506573761', 'a786b40d-6cbb-45c1-9182-f6cb67ae2783');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('7559afd6-ce31-4185-a7f2-e259a0faa863', null, null, null, null, false, null, null, 137580, 7858,
        '95d51306-700b-47bc-932b-2f353099944a', '9dbb5a86-1216-4564-b34f-e7d255c2eb50');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('50d50a43-1653-40ed-8e65-7b9d7285357c', null, null, null, null, false, null, null, 129785, 6660,
        '9dbb5a86-1216-4564-b34f-e7d255c2eb50', '95d51306-700b-47bc-932b-2f353099944a');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('e4639e9e-9ddb-445e-acb6-88e076de1412', null, null, null, null, false, null, null, 25896, 2256,
        '5b0981ca-f393-4617-bcb6-84d0e2d06c3d', '1b053d4a-3430-47aa-baf5-b63780b71bc8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('ddcf0365-7b7f-41fd-991e-e66e103a3d5a', null, null, null, null, false, null, null, 26389, 2594,
        '1b053d4a-3430-47aa-baf5-b63780b71bc8', '5b0981ca-f393-4617-bcb6-84d0e2d06c3d');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('c486c34b-d995-4474-a99c-cd7c4e1eedcc', null, null, null, null, false, null, null, 31131, 2422,
        '1b053d4a-3430-47aa-baf5-b63780b71bc8', '831ca0f4-1393-425b-9b30-0bd60bd348a7');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('db273206-c9b6-4a66-b202-0f96fdcb0306', null, null, null, null, false, null, null, 29478, 2171,
        '831ca0f4-1393-425b-9b30-0bd60bd348a7', '1b053d4a-3430-47aa-baf5-b63780b71bc8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('7a435b71-bfa1-44f0-a971-d0a18d8d5f16', null, null, null, null, false, null, null, 16129, 1047,
        'ccab9b17-788d-402c-9945-050c4cb68004', '5ab105ae-fe3a-4ec8-971c-97e06508e6d6');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('3e008945-01b5-466f-a58f-f0700c619d66', null, null, null, null, false, null, null, 16370, 1200,
        '5ab105ae-fe3a-4ec8-971c-97e06508e6d6', 'ccab9b17-788d-402c-9945-050c4cb68004');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('0466cadf-5667-4eef-9148-76c55a2e64c1', null, null, null, null, false, null, null, 53625, 2844,
        '5ab105ae-fe3a-4ec8-971c-97e06508e6d6', '99e00940-9bfe-4961-b6a1-57c6bc6d102f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('9a778fe6-224e-453c-90c4-b167e5229cc6', null, null, null, null, false, null, null, 52718, 2608,
        '99e00940-9bfe-4961-b6a1-57c6bc6d102f', '5ab105ae-fe3a-4ec8-971c-97e06508e6d6');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('1fbf5d91-d6a1-401f-94c6-3b9eb8850a23', null, null, null, null, false, null, null, 104611, 4545,
        '99e00940-9bfe-4961-b6a1-57c6bc6d102f', '95d51306-700b-47bc-932b-2f353099944a');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('617bb094-af5d-4952-8f65-500364547d91', null, null, null, null, false, null, null, 103406, 5882,
        '95d51306-700b-47bc-932b-2f353099944a', '99e00940-9bfe-4961-b6a1-57c6bc6d102f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('737d31fa-d956-449f-8fcb-b6b7d90d1351', null, null, null, null, false, null, null, 14141, 1150,
        '831ca0f4-1393-425b-9b30-0bd60bd348a7', '99e00940-9bfe-4961-b6a1-57c6bc6d102f');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('efe67f57-4c13-4e5a-a848-ce43fafef26b', null, null, null, null, false, null, null, 13591, 1280,
        '99e00940-9bfe-4961-b6a1-57c6bc6d102f', '831ca0f4-1393-425b-9b30-0bd60bd348a7');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('242cd517-e6f2-40f2-9d80-4194b155ba45', null, null, null, null, false, null, null, 156091, 7077,
        '1b053d4a-3430-47aa-baf5-b63780b71bc8', '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('0198fa27-450a-4209-b62f-fcb19d43b1b7', null, null, null, null, false, null, null, 154447, 9791,
        '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13', '1b053d4a-3430-47aa-baf5-b63780b71bc8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('0e58397d-19bd-405c-8993-5bd1c7f913e0', null, null, null, null, false, null, null, 119609, 7847,
        '95d51306-700b-47bc-932b-2f353099944a', '1b053d4a-3430-47aa-baf5-b63780b71bc8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('fd7c0b94-9227-4e7f-9ab4-c49f4c7a3010', null, null, null, null, false, null, null, 123989, 5257,
        '1b053d4a-3430-47aa-baf5-b63780b71bc8', '95d51306-700b-47bc-932b-2f353099944a');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('5ab089e0-7f56-40a1-816f-8f56091d4500', null, null, null, null, false, null, null, 36385, 2421,
        '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13', '95d51306-700b-47bc-932b-2f353099944a');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('45a3ad1a-1387-43e9-b645-20eb0547988f', null, null, null, null, false, null, null, 36395, 2335,
        '95d51306-700b-47bc-932b-2f353099944a', '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('ff368916-4749-4cb7-988c-588f774aab3d', null, null, null, null, false, null, null, 20256, 1731,
        '02e65e5e-7a8a-49f0-b5e8-8277f399e797', '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('4651adad-e9a5-4b66-95a8-26cd53af7c88', null, null, null, null, false, null, null, 20994, 1835,
        '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13', '02e65e5e-7a8a-49f0-b5e8-8277f399e797');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('6ede9d53-a3b7-4011-bfbd-ccd49d6768e8', null, null, null, null, false, null, null, 88283, 3959,
        '048ce871-1386-45cf-a5f4-0a9fea0970a7', '02e65e5e-7a8a-49f0-b5e8-8277f399e797');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('9e364ea6-bf70-48e8-9f96-ac3e41233198', null, null, null, null, false, null, null, 88275, 3989,
        '02e65e5e-7a8a-49f0-b5e8-8277f399e797', '048ce871-1386-45cf-a5f4-0a9fea0970a7');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('ec48154c-167f-4ee8-bc50-84b0909be690', null, null, null, null, false, null, null, 105727, 4590,
        'bd801f36-6151-4531-a391-c9f0e740763e', '048ce871-1386-45cf-a5f4-0a9fea0970a7');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('521cfbc1-dd34-42b2-93ab-7f78528af594', null, null, null, null, false, null, null, 104777, 4588,
        '048ce871-1386-45cf-a5f4-0a9fea0970a7', 'bd801f36-6151-4531-a391-c9f0e740763e');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('7acaa53c-5099-4581-b286-3f8fc37a213b', null, null, null, null, false, null, null, 106294, 4524,
        '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13', '9209b67e-0829-4f92-9353-b408b2948568');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('36c16e84-d9a9-4257-8c81-86d0b3937845', null, null, null, null, false, null, null, 109676, 4810,
        '9209b67e-0829-4f92-9353-b408b2948568', '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('e580cf28-f520-4d2c-a1e8-27b425876752', null, null, null, null, false, null, null, 39278, 1782,
        '9209b67e-0829-4f92-9353-b408b2948568', 'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('5fe5a71c-f418-4943-95cc-7d1bb9b6628f', null, null, null, null, false, null, null, 42760, 1993,
        'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8', '9209b67e-0829-4f92-9353-b408b2948568');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('d642f09f-84cd-409e-b88c-7cadae74a1a1', null, null, null, null, false, null, null, 45482, 2586,
        'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8', 'a1115838-0026-42c4-a691-a4f9fb97c3d2');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('7894c2b9-7634-4cf4-8ed2-b0079810b0d0', null, null, null, null, false, null, null, 32189, 3241,
        'a1115838-0026-42c4-a691-a4f9fb97c3d2', 'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('73f35e31-1e38-4be2-8523-7d36d0afcb5a', null, null, null, null, false, null, null, 107581, 5600,
        'a1115838-0026-42c4-a691-a4f9fb97c3d2', 'c04f4f3e-356f-4f06-a512-87abf6034839');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('5d98511f-c833-475a-8090-afc642ef7703', null, null, null, null, false, null, null, 106459, 5803,
        'c04f4f3e-356f-4f06-a512-87abf6034839', 'a1115838-0026-42c4-a691-a4f9fb97c3d2');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('0742fa2f-7fa0-43f0-aaa6-d7b2e1d90bb9', null, null, null, null, false, null, null, 117499, 4951,
        'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8', '87837776-b83f-436c-a79d-1c1032e60383');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('5e31ee6a-8530-4b01-8ed3-12251af7f28a', null, null, null, null, false, null, null, 115509, 4706,
        '87837776-b83f-436c-a79d-1c1032e60383', 'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('2db28ed9-ba93-4861-a182-83cb4992fade', null, null, null, null, false, null, null, 17388, 1190,
        '87837776-b83f-436c-a79d-1c1032e60383', '04ec85ff-c243-47d7-b7ed-1e656c94bb7d');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('c9c475bd-92f7-4896-b2a2-5656b9ac2e78', null, null, null, null, false, null, null, 14567, 1363,
        '04ec85ff-c243-47d7-b7ed-1e656c94bb7d', '87837776-b83f-436c-a79d-1c1032e60383');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('3fba4ee2-8c65-4687-939f-9815c5bd214d', null, null, null, null, false, null, null, 34755, 2193,
        'c94e1e2b-1658-4a54-b705-6123c1fef219', '87837776-b83f-436c-a79d-1c1032e60383');
INSERT INTO S_HUB.p_hub_path (hub_path_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                              updated_by, distance, duration, from_hub_id, to_hub_id)
VALUES ('b2efdfbe-67ea-4c0a-b970-f776bf543cd3', null, null, null, null, false, null, null, 33479, 2070,
        '87837776-b83f-436c-a79d-1c1032e60383', 'c94e1e2b-1658-4a54-b705-6123c1fef219');


CREATE TABLE S_HUB.manager
(
    manager_id INTEGER PRIMARY KEY,
    hub_id     UUID,
    type       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP,
    deleted_by UUID,
    is_deleted BOOLEAN
);

INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (2, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'a786b40d-6cbb-45c1-9182-f6cb67ae2783');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (3, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '9dbb5a86-1216-4564-b34f-e7d255c2eb50');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (4, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'ccab9b17-788d-402c-9945-050c4cb68004');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (5, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '0062c835-6d53-4c07-9b7b-8dafabd04baa');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (6, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '6e70e165-cf6c-41c4-a540-aa9506573761');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (7, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '1b053d4a-3430-47aa-baf5-b63780b71bc8');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (8, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '5ab105ae-fe3a-4ec8-971c-97e06508e6d6');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (9, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '831ca0f4-1393-425b-9b30-0bd60bd348a7');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (10, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '99e00940-9bfe-4961-b6a1-57c6bc6d102f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (11, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '5b0981ca-f393-4617-bcb6-84d0e2d06c3d');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (12, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '87837776-b83f-436c-a79d-1c1032e60383');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (13, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'c94e1e2b-1658-4a54-b705-6123c1fef219');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (14, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'c04f4f3e-356f-4f06-a512-87abf6034839');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (15, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '04ec85ff-c243-47d7-b7ed-1e656c94bb7d');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (16, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'a1115838-0026-42c4-a691-a4f9fb97c3d2');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (17, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '9209b67e-0829-4f92-9353-b408b2948568');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (18, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'fea57c6b-4b6f-43bb-95ef-5100ca2e47c8');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (19, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '0d3df28e-d51c-4e8f-a36e-ae7b8d944c13');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (20, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '02e65e5e-7a8a-49f0-b5e8-8277f399e797');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (21, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '95d51306-700b-47bc-932b-2f353099944a');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (22, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', 'bd801f36-6151-4531-a391-c9f0e740763e');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (23, null, null, null, null, false, null, null, 'TO_COMPANY_DELIVERY', '048ce871-1386-45cf-a5f4-0a9fea0970a7');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1002, null, null, null, null, false, null, null, 'HUB_TO_HUB_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1001, null, null, null, null, false, null, null, 'HUB_TO_HUB_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1003, null, null, null, null, false, null, null, 'HUB_TO_HUB_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1004, null, null, null, null, false, null, null, 'HUB_TO_HUB_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
INSERT INTO S_HUB.manager (manager_id, created_at, created_by, deleted_at, deleted_by, is_deleted, updated_at,
                           updated_by, type, hub_id)
VALUES (1005, null, null, null, null, false, null, null, 'HUB_TO_HUB_DELIVERY', '2d92d971-b752-48b8-a394-7a9f47540c4f');
