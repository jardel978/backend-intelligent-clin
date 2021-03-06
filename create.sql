create sequence sq_tb_consulta start with 1 increment by 1;
create sequence sq_tb_dentista start with 1 increment by 1;
create sequence sq_tb_endereco start with 1 increment by 1;
create sequence sq_tb_file start with 1 increment by 1;
create sequence sq_tb_paciente start with 1 increment by 1;
create sequence sq_tb_prontuario start with 1 increment by 1;
create sequence sq_tb_usuario start with 1 increment by 1;
create table dentista_especialidades (dentista_dentista_id bigint not null, dentista_especialidades varchar(255));
create table tb_consulta (consulta_id bigint not null, complemento varchar(255), data_atualizacao_status timestamp, data_consulta date, hora_consulta time, status varchar(255), valor decimal(5,2), dentista_id bigint, paciente_id bigint, usuario_id bigint, primary key (consulta_id));
create table tb_dentista (dentista_id bigint not null, cpf varchar(255), data_cadastro timestamp, email varchar(255), nome varchar(255), sobrenome varchar(255), telefone varchar(255), matricula varchar(255), primary key (dentista_id));
create table tb_endereco (endereco_id bigint not null, bairro varchar(255), cep varchar(255), cidade varchar(255), complemento varchar(255), estado varchar(255), numero varchar(255), rua varchar(255), primary key (endereco_id));
create table tb_file (file_id bigint not null, data bytea, data_atualizacao timestamp, data_criacao timestamp, nome varchar(255), tipo varchar(255), prontuario_id bigint, primary key (file_id));
create table tb_paciente (paciente_id bigint not null, cpf varchar(255), data_cadastro timestamp, email varchar(255), nome varchar(255), sobrenome varchar(255), telefone varchar(255), data_nascimento date, sexo varchar(255), endereco_id bigint, prontuario_id bigint, primary key (paciente_id));
create table tb_prontuario (prontuario_id bigint not null, data_criacao timestamp, evolucao_tratamento varchar(800), plano_tratamento varchar(800), ultima_alteracao timestamp, dentista_id bigint, file_id bigint, paciente_id bigint, primary key (prontuario_id));
create table tb_usuario (usuario_id bigint not null, cpf varchar(255), data_cadastro timestamp, email varchar(255), nome varchar(255), sobrenome varchar(255), telefone varchar(255), cargo varchar(255), senha varchar(255), primary key (usuario_id));
alter table tb_dentista add constraint UK_nh8f3hhs7vgux1jqgvrkypjs3 unique (cpf);
alter table tb_dentista add constraint UK_59bwb00e3g12sxeaw6i1c4u4r unique (email);
alter table tb_dentista add constraint UK_5ivvaudlypnekhsbg983nsb59 unique (matricula);
alter table tb_paciente add constraint UK_6ad9be3a7jjmtjcos2y16mo08 unique (cpf);
alter table tb_paciente add constraint UK_s0br0snl91alxletgeh8m105r unique (email);
alter table tb_usuario add constraint UK_594wib8ansybtilla48x7vdld unique (cpf);
alter table tb_usuario add constraint UK_spmnyb4dsul95fjmr5kmdmvub unique (email);
alter table dentista_especialidades add constraint FKkdno5nit5hlwbvqq3lkhsa7w6 foreign key (dentista_dentista_id) references tb_dentista;
alter table tb_consulta add constraint fk_dentista foreign key (dentista_id) references tb_dentista;
alter table tb_consulta add constraint fk_paciente foreign key (paciente_id) references tb_paciente;
alter table tb_consulta add constraint fk_usuario foreign key (usuario_id) references tb_usuario;
alter table tb_file add constraint fk_prontuario_file foreign key (prontuario_id) references tb_prontuario;
alter table tb_paciente add constraint fk_endereco foreign key (endereco_id) references tb_endereco;
alter table tb_paciente add constraint fk_prontuario_paciente foreign key (prontuario_id) references tb_prontuario;
alter table tb_prontuario add constraint fk_dentista_prontuario foreign key (dentista_id) references tb_dentista;
alter table tb_prontuario add constraint fk_file_prontuario foreign key (file_id) references tb_file;
alter table tb_prontuario add constraint fk_paciente_prontuario foreign key (paciente_id) references tb_paciente;
