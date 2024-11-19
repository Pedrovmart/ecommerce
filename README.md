
# 🛒 E-commerce Web Application

Este projeto é um aplicativo web de **e-commerce** que permite a busca e compra de produtos eletrônicos. Ele utiliza as tecnologias **Jakarta EE**, **PrimeFaces**, **JPA**, **CDI**, **EJB** e **H2 Database** para simular um ambiente completo de compras online.

## 🚀 Funcionalidades Principais

- 🔍 **Busca de Produtos**: Exibe uma lista de produtos com imagens e detalhes.
- 🛠️ **Filtragem de Produtos**: Filtros por categoria e faixa de preço.
- 🛒 **Gerenciamento de Carrinho**: Adicionar, remover e listar produtos no carrinho de compras.
- ✅ **Finalização de Compra**: Simula a finalização da compra (sem processamento de pagamento).
- 🔐 **Login Simulado**: Botão de login para navegação (simulação).

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Jakarta EE**
- **PrimeFaces**
- **JPA (Jakarta Persistence API)**
- **Banco de dados H2**
- **WildFly**

## 🗂️ Estrutura de Pastas

```plaintext
📦 src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 br.com.ecommerce
 ┃ ┃ ┃ ┣ 📂 beans
 ┃ ┃ ┃ ┃ ┣ 📝 CarrinhoBean.java
 ┃ ┃ ┃ ┃ ┗ 📝 ProdutoBean.java
 ┃ ┃ ┃ ┣ 📂 domain
 ┃ ┃ ┃ ┃ ┣ 📝 Carrinho.java
 ┃ ┃ ┃ ┃ ┣ 📝 Produto.java
 ┃ ┃ ┃ ┃ ┗ 📝 Usuario.java
 ┃ ┃ ┃ ┣ 📂 dto
 ┃ ┃ ┃ ┃ ┗ 📝 ProdutoDTO.java
 ┃ ┃ ┃ ┗ 📂 services
 ┃ ┃ ┃ ┃ ┗ 📝 ProdutoService.java
 ┃ ┣ 📂 resources
 ┃ ┃ ┣ 📂 META-INF
 ┃ ┃ ┃ ┗ 📝 persistence.xml
 ┃ ┃ ┗ 📝 data.sql
 ┃ ┣ 📂 webapp
 ┃ ┃ ┣ 📂 WEB-INF
 ┃ ┃ ┃ ┣ 📂 layout
 ┃ ┃ ┃ ┃ ┣ 📝 footer.xhtml
 ┃ ┃ ┃ ┃ ┣ 📝 header.xhtml
 ┃ ┃ ┃ ┃ ┗ 📝 layout.xhtml
 ┃ ┃ ┃ ┣ 📝 beans.xml
 ┃ ┃ ┃ ┗ 📝 faces-config.xml
 ┃ ┃ ┣ 📝 busca.xhtml
 ┃ ┃ ┣ 📝 carrinho.xhtml
 ┃ ┃ ┣ 📝 index.html
 ┃ ┃ ┣ 📝 login.xhtml
 ┃ ┃ ┗ 📝 produtos.xhtml
```


## 🛠️ Requisitos

- **JDK 17**
- **Maven**
- **WildFly**
- **Banco de dados H2**

## ⚙️ Instalação e Execução

### 1. Configuração do Banco de Dados

O projeto utiliza o banco de dados em memória **H2**. Para acessar o console do H2, siga as instruções:

- **JDBC URL**: `jdbc:h2:mem:test;DB_CLOSE_DELAY=-1`
- **Usuário**: `sa`
- **Senha**: (deixe em branco)

### 3. Compilação e Execução

1. Compile o projeto usando Maven:

```bash
mvn clean package
```

2. Faça o deploy no WildFly:

```bash
cp target/seu-projeto.war $WILDFLY_HOME/standalone/deployments/
```

3. Inicie o WildFly:

```bash
$WILDFLY_HOME/bin/standalone.sh
```

4. Acesse a aplicação:

```
http://localhost:8080/seu-projeto
```

## 🗄️ Script SQL para Popular o Banco de Dados

O arquivo `data.sql` inclui inserções para popular o banco de dados com produtos eletrônicos. Exemplo de inserção:

```sql
INSERT INTO Produto (nome, descricao, preco, categoria, imagem, estoque) 
VALUES ('Smartphone', 'Smartphone 5G com câmera de 108MP', 2500.00, 'Eletronicos', 'https://link-para-imagem-smartphone.jpg', 50);
```

## 💡 Funcionalidades Detalhadas

### 1. 🌐 **Busca de Produtos**

- O usuário pode buscar por produtos usando filtros de categoria e faixa de preço.

### 2. 🛒 **Carrinho de Compras**

- Adicionar produtos ao carrinho.
- Remover produtos do carrinho.
- Exibir detalhes do carrinho como preço total, quantidade e descrição.

### 3. 🔐 **Login**

- O botão de login simula a navegação para uma página de autenticação.

### 4. 🎨 **Imagens dos Produtos**

- As imagens dos produtos são carregadas a partir de URLs fornecidas pelo `Faker`.

## ✨ Contribuições

Contribuições são bem-vindas! Caso queira contribuir, siga as diretrizes de contribuição e abra um pull request.


## 👥 Contato

- Desenvolvido por Pedro Vieira Martinelli e Pedro dias
