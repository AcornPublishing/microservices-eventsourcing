INSERT INTO TB_CART (CART_ID, DELETED, SEQUENCE, SNAPSHOT_PAYLOAD, SNAPSHOT_SEQ, SNAPSHOT_TIME, VERSION) VALUES ('id9xf8w1', false, 1, '{"cartId":"id9xf8w1","items":[],"version":0}', 1, 1663194714176, 0);
INSERT INTO TB_CART_EVENT (EVENT_ID, CART_ID, CORRELATION_ID, DELETED, EVENT_TYPE, PAYLOAD, PROPAGATE, RELAYED, SEQUENCE) VALUES ('a5cee1ee-9aa2-4593-8754-1a0488de3dc0', 'id9xf8w1', null, false,	'io.cosmos.cart.event.CartCreated', '{"cartId":"id9xf8w1"}', true, true, 1);