DELIMITER //

create trigger before_insert_pedido_importacao
before insert on pedido_importacao
for each row
begin
    if new.uuid is null then
        set new.uuid = UUID();
    end if;
end;
//

DELIMITER ;