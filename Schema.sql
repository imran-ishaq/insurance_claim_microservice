create table customer
(
    date_of_birth   date         not null,
    created_at      timestamp(6) not null,
    updated_at      timestamp(6) not null,
    id              uuid         not null
        primary key,
    full_name       varchar(255) not null,
    insurance_types jsonb
);

alter table customer
    owner to root;

create table insurance_claim
(
    claim_type smallint         not null
        constraint insurance_claim_claim_type_check
            check ((claim_type >= 0) AND (claim_type <= 2)),
    cost       double precision not null,
    date       date             not null,
    created_at timestamp(6)     not null,
    updated_at timestamp(6)     not null,
    id         uuid             not null
        primary key,
    user_id    uuid             not null
        constraint fkocxubulieuybgpsx2k4n2oaht
            references customer,
    status     varchar(255)     not null
        constraint insurance_claim_status_check
            check ((status)::text = ANY
        ((ARRAY ['OPEN'::character varying, 'CLOSED'::character varying, 'PENDING'::character varying, 'APPROVED'::character varying])::text[]))
    );

alter table insurance_claim
    owner to root;