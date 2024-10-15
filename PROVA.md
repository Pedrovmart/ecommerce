# E-commerce de Produtos Eletrônicos
Objetivo: Avaliar a capacidade do aluno em aplicar os conceitos de Jakarta EE, incluindo JPA, CDI, EJB e JSF, para a construção de uma aplicação web que simula um sistema de e-commerce de produtos eletrônicos.
## Instruções:

1. Crie um novo projeto Maven com as dependências necessárias para Jakarta EE.
2. Implemente uma aplicação web que atenda aos requisitos abaixo.
3. Utilize os recursos de JPA para gerenciar dados de um banco de dados.
4. Utilize CDI para injeção de dependências.
5. Utilize EJBs para implementar a lógica de negócio.
6. Utilize JSF para a interface do usuário.
7. Utilize o banco de dados H2 para testes.
8. Comente seu código: Explique o propósito de cada classe, método e atributo. Utilize comentários para explicar as decisões de design e o uso de cada tecnologia Jakarta EE.
9. A aplicação deve ser estruturada de forma organizada, seguindo as boas práticas de desenvolvimento.

## Requisitos:

### Entidades:

#### Crie uma entidade chamada `domain.br.com.ecommerce.Produto` com os seguintes atributos:
- id: Integer (chave primária, auto-incremento)
- nome: String
- descricao: String
- preco: Double
- categoria: String
- imagem: String (URL da imagem do produto)
- estoque: Integer
#### Crie uma entidade chamada `domain.br.com.ecommerce.Usuario` com os seguintes atributos:
- id: Integer (chave primária, auto-incremento)
- nome: String
- email: String
- senha: String
#### Crie uma entidade chamada `domain.br.com.ecommerce.Carrinho` com os seguintes atributos:
- id: Integer (chave primária, auto-incremento)
- usuario: domain.br.com.ecommerce.Usuario (relacionamento many-to-one com domain.br.com.ecommerce.Usuario)
- produtos: List<domain.br.com.ecommerce.Produto> (relacionamento many-to-many com domain.br.com.ecommerce.Produto)
- total: Double (valor total do carrinho)

### EJB:

#### Crie um EJB Stateless chamado ProdutoService com os seguintes métodos:
- `buscarProduto(Integer id)`: Busca um produto pelo ID.
- `listarProdutos()`: Retorna a lista de todos os produtos.
- `listarProdutosPorCategoria(String categoria)`: Retorna a lista de produtos de uma determinada categoria.
- `adicionarProdutoAoCarrinho(domain.br.com.ecommerce.Usuario usuario, Integer idProduto)`: Adiciona um produto ao carrinho do usuário. Dica: Use a entidade domain.br.com.ecommerce.Carrinho para armazenar a lista de produtos e o valor total.
- `removerProdutoDoCarrinho(domain.br.com.ecommerce.Usuario usuario, Integer idProduto)`: Remove um produto do carrinho do usuário. Dica: Use a entidade domain.br.com.ecommerce.Carrinho para atualizar a lista de produtos e o valor total.
- `calcularTotalCarrinho(domain.br.com.ecommerce.Usuario usuario)`: Calcula o valor total do carrinho do usuário.

### CDI:

Injete o `ProdutoService` no Managed Bean que será usado na interface JSF.

### JSF:

#### Crie uma página JSF chamada `produtos.xhtml` com os seguintes elementos:

- Uma seção para exibir a lista de produtos, com:
    - Título do produto.
    - Descrição do produto.
    - Preço do produto.
    - Imagem do produto.
    - Botão "Adicionar ao domain.br.com.ecommerce.Carrinho".

#### Crie um formulário para o usuário realizar login, com campos para:
- E-mail.
- Senha.

#### Crie um formulário para o usuário realizar cadastro, com campos para:
- Nome.
- E-mail.
- Senha.

#### Crie uma página JSF chamada `carrinho.xhtml` para exibir o carrinho do usuário, com:
- Lista de produtos no carrinho.
- Quantidade de cada produto.
- Preço total do carrinho.
- Botão "Remover domain.br.com.ecommerce.Produto".
- Botão "Finalizar Compra" (implementação simulada, sem processamento de pagamento).

#### Utilize templates para o cabeçalho e rodapé

### Banco de dados:

Crie as tabelas produtos, usuarios e carrinho no banco de dados H2 com os campos correspondentes às entidades. Utilize a inicialização automática do JPA para essa tarefa.

## Observações:
- O código fonte deve ser escrito em Java.
- Utilize as melhores práticas de desenvolvimento web para construir a aplicação.
- Utilize o Maven para gerenciar as dependências do projeto.
- Simule a funcionalidade de login e cadastro, sem validação de senha ou autenticação real.
- Simule a funcionalidade de finalizar compra, sem processamento de pagamento real.
- Explique os principais conceitos do Jakarta EE: Utilize comentários para explicar os conceitos de JPA, CDI, EJB e JSF, como:
    - JPA: Mapeamento objeto-relacional, persistência de objetos, gerenciamento de transações.
    - CDI: Injeção de dependências, gerenciamento de escopo, interceptores.
    - EJB: Componentes de negócio, transações, segurança, timers.
    -  JSF: Desenvolvimento de interfaces web, componentes, validação, conversão.

## Entrega (Peso 6):
- Submeta o projeto Maven com o código fonte da aplicação.
- Inclua um README.md com instruções sobre como executar a aplicação.

## Critérios de Avaliação:
- Projeto funcional.
- Implementação correta das entidades, EJBs, CDI e JSF.
- Funcionalidade completa da aplicação, incluindo a simulação de um sistema de e-commerce de produtos eletrônicos.
- Código limpo, organizado e comentado.
- Comentários explicando o código e os conceitos do Jakarta EE.
- Uso adequado das tecnologias Jakarta EE.

## Tarefa Extra Obrigatória (Peso 4):
Após a entrega da prova, estude e implemente uma das funcionalidades adicionais abaixo, utilizando os recursos do Jakarta EE:

### Opção 1: Sistema de Recomendação de Produtos:
Implemente um sistema de recomendação que sugere produtos para o usuário com base em suas compras anteriores ou na categoria dos produtos que ele visualizou.

### Opção 2: Busca por Filtros:
Permita que os usuários filtrem os produtos por categoria, faixa de preço, avaliação média, etc.

### Entrega da Tarefa Extra Obrigatória:
- Submeta o código fonte da aplicação com a implementação da funcionalidade adicional escolhida.
- Inclua um README.md com instruções sobre como executar a aplicação com a nova funcionalidade.

### Critérios de Avaliação:
- Implementação correta das entidades, EJBs, CDI e JSF.
- Funcionalidade completa da aplicação, incluindo a simulação de um sistema de e-commerce de produtos eletrônicos.
- Código limpo, organizado e comentado.
- Comentários explicando o código e os conceitos do Jakarta EE.
- Uso adequado das tecnologias Jakarta EE.