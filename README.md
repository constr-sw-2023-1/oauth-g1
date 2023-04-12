# Oauth01
API para autenticação utilizando 
[Keycloak](https://www.keycloak.org/)

Você vai precisar do [Docker](https://www.docker.com) instalado em sua máquina, rode o seguinte comando na raiz do projeto:

    docker compose up
Após as imagens subirem, entre no [console do Keycloak](https://localhost:8090/auth) e configure conforme suas necessidades.

Para logar suas credencias de admin serão as mesmas configuradas no docker compose:
- KEYCLOAK_USER
- KEYCLOAK_PASSWORD

Então deve-se configurar um realm, um client_id e um client_secret nas respectivas variáveis:

- KEYCLOAK_REALM
- KEYCLOAK_CLIENT_ID
- KEYCLOAK_CLIENT_SECRET

Depois rode o comando:

    docker compose restart


# Erros

| OA-10X | 401 Unauthorized |
| ------ | ----------------: |
| OA-101 | Não autorizado |
| OA-102 | Token inválido ou expirado |
| OA-103 | Credenciais inválidas |

---

| OA-20X | 403  Forbidden |
| ------ | -----------: |
| OA-201 | Acesso negado |

---

| OA-30X | 404 - Not Found|
| ------ | -----------: |
| OA-301 | Não encontrado |

---

| OA-40X| 400 - Bad Request|
| ------ | -----------: |
| OA-401| Requisição inválida|

---

| OA-50X| 500 - Internal Server Error|
| ------ | -----------: |
| OA-501| Erro interno da aplicação |
