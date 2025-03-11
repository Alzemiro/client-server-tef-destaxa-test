## Simulador de Autorização de Pagamento Destaxa

Este projeto foi desenvolvido para o teste da Destaxa, com o objetivo de simular um sistema de autorização de pagamento. Ele demonstra habilidades de desenvolvimento Java, incluindo a construção de uma API REST com Spring Boot, comunicação socket com o protocolo ISO8583 e o uso de threads para processamento concorrente.

**Arquitetura**

O sistema é dividido em duas aplicações:

* **Cliente (API):** Uma aplicação Spring Boot que recebe requisições de autorização, as converte para o formato ISO8583 e as envia para o servidor de autorização.
* **Servidor (Autorizador):** Um servidor socket que recebe mensagens ISO8583, processa as requisições de autorização e envia as respostas.

**Como executar**

1. **Requisitos:**
    * Java Development Kit (JDK) 17 ou superior
    * Maven
    * IDE Java (ex: Eclipse, IntelliJ)
2. **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/client-server-tef-destaxa-test.git
    ```
3. **Importe os projetos:**
    * Importe os projetos `payment-api` e `autorize-server` na sua IDE.
4. **Execute o servidor:**
    * Execute a classe `AuthorizeServerApplication` no projeto `autorize-server`.
5. **Execute a API:**
    * Execute a classe `PaymentApiApplication` no projeto `payment-api`.
6. **Envie requisições:**
    * Envie requisições POST para o endpoint `/authorization` da API, utilizando o formato JSON descrito abaixo.

**Exemplo de Requisição**

```json
{
  "external_id": 12345,
  "value": 23.80,
  "card_number": "1234567890123456",
  "cvv": "123",
  "exp_month": "11",
  "exp_year": "28",
  "holder_name": "Destaxa",
  "installments": 2
}
```

**Destaques da Arquitetura**

* **Clean Architecture:** O projeto segue os princípios da Clean Architecture, com camadas bem definidas (core, application, infraestructure) e dependências direcionadas para o centro. Isso torna o código mais organizado, testável e independente de frameworks e tecnologias externas.
* **Escalabilidade:** O uso de threads no servidor permite o processamento concorrente de múltiplas requisições, tornando o sistema mais escalável.
* **Comunicação Eficiente:** A comunicação cliente-servidor é baseada em sockets, o que garante um bom desempenho e a capacidade de lidar com um grande volume de requisições.
* **Padrões de Design:** O uso do padrão Mapper contribui para a clareza e organização do código, desacoplando o mapeamento de objetos de domínio e mensagens ISO8583.

**Considerações**

* Aspectos de segurança foram simplificados para este teste.
* Não há persistência em banco de dados.
* O projeto utiliza o framework Spring Boot e a biblioteca jPOS para manipular mensagens ISO8583.