--INSERT INTO TB_ORDER VALUES ('3d835dce', false, 2, '{"no":"3d835dce","items":[{"no":"","product":{"no":"PRD001","name":"MacBook Pro 16","price":4710000},"quantity":3,"total":14130000}],"shippingAddress":{"zipCode":"12345","baseAddress":"시군구","detailAddress":"읍면동"},"orderer":{"no":"446a382e","name":"cosmos"},"state":"Completed","reason":null,"version":0}', 1658056700659, 1);
--INSERT INTO TB_ORDER_EVENT VALUES ('df0b2467-9e59-417a-9c24-4c1ae27b53b6', false, 'io.cosmos.order.event.OrderPlaced', '3d835dce', '{"no":"3d835dce","items":[{"no":"","product":{"no":"PRD001","name":"MacBook Pro 16","price":4710000},"quantity":3,"total":14130000}],"shippingAddress":{"zipCode":"12345","baseAddress":"시군구","detailAddress":"읍면동"},"orderer":{"no":"446a382e","name":"cosmos"}}', true, false, 1);

INSERT INTO TB_ORDER VALUES ('z76a0235', false, 2, null, 0, 0);
INSERT INTO TB_ORDER_EVENT VALUES ('edca8c50-24a0-4567-89ad-b8d441dd6fe0', false, 'io.cosmos.order.event.OrderPlaced', 'z76a0235', '{"no":"z76a0235","items":[{"no":"","product":{"no":"PRD001","name":"MacBook Pro 16''","price":4710000},"quantity":3,"total":14130000}],"shippingAddress":{"zipCode":"12345","baseAddress":"시군구","detailAddress":"읍면동"},"orderer":{"no":"fc60e36e","name":"cosmos"}}', true, false, '1', 1);
INSERT INTO TB_ORDER_EVENT VALUES ('edca8c50-24a0-4567-89ad-b8d441dd6fe1', false, 'io.cosmos.order.event.OrderCanceled', 'z76a0235', '{"no":"z76a0235"}', true, false, '1', 2);
--
-- INSERT INTO TB_ORDER VALUES ('3522c576', false, 2, 0);
-- INSERT INTO TB_ORDER_EVENT VALUES ('447c1154-dbf0-4395-9bc8-35ec850c06de', false, 'io.cosmos.order.event.OrderPlaced', '3522c576', '{"no":"3522c576","items":[{"no":"","product":{"no":"PRD001","name":"MacBook Pro 16''","price":4710000},"quantity":3,"total":14130000}],"shippingAddress":{"zipCode":"12345","baseAddress":"시군구","detailAddress":"읍면동"},"orderer":{"no":"fc60e36e","name":"cosmos"}}', true, false, 1);
-- INSERT INTO TB_ORDER_EVENT VALUES ('c846fae2-dfae-4bc2-8916-53c4930883ad', false, 'io.cosmos.order.event.OrderCompleted', '3522c576', '{"no":"3522c576"}', true, false, 2);
--
-- INSERT INTO TB_ORDER VALUES ('b4c0cd8e', false, 3, 0);
-- INSERT INTO TB_ORDER_EVENT VALUES ('51ace65d-8260-41f7-ace6-3cf381e58f31', false, 'io.cosmos.order.event.OrderPlaced', 'b4c0cd8e', '{"no":"b4c0cd8e","items":[{"no":"","product":{"no":"PRD001","name":"MacBook Pro 16''","price":4710000},"quantity":3,"total":14130000}],"shippingAddress":{"zipCode":"12345","baseAddress":"시군구","detailAddress":"읍면동"},"orderer":{"no":"fc60e36e","name":"cosmos"}}', true, false, 1);
-- INSERT INTO TB_ORDER_EVENT VALUES ('51ace65d-8260-41f7-ace6-3cf381e58f32', false, 'io.cosmos.order.event.OrderCompleted', 'b4c0cd8e', '{"no":"b4c0cd8e"}', true, false, 2);
-- INSERT INTO TB_ORDER_EVENT VALUES ('51ace65d-8260-41f7-ace6-3cf381e58f33', false, 'io.cosmos.order.event.OrderDeliveryBegan', 'b4c0cd8e', '{"no":"b4c0cd8e"}', true, false, 3);