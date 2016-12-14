-- users in system
insert into account(account_name , password) values('yusuf', 'bahadir');
insert into account(account_name , password) values('kubra', 'nil');


-- oauth client details
insert into client_details(   client_id, client_secret,  resource_ids,   scopes,   grant_types,                                  authorities)
                    values(   'acme' ,  'acmesecret',    null,           'openid,read',   'authorization_code,refresh_token,password',  'ROLE_ADMIN,ROLE_USER' );