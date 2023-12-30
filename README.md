# challenge-user-car

# Sistema de Usuários de Carros - API RESTful

Este projeto implementa um Sistema de Usuários de Carros, utilizando Java, Spring Framework, JWT para autenticação, H2 como banco de dados em memória, e Maven para o processo de build.

## Estórias de Usuário

1) **Criação de Usuários e Carros:**
   - Como um novo usuário, desejo me cadastrar na aplicação para poder utilizar os serviços.

2) **Edição dos Carros e Usuários:**
   - Como usuário cadastrado, desejo fazer login na aplicação para acessar recursos protegidos. (Descrição: Esta estória refere-se à capacidade de usuários autenticados editarem informações dos carros associados às suas contas e também realizar edições em seus próprios perfis de usuário.)

3) **Exclusão de Carros e Usuários:**
   - Como usuário autenticado, desejo excluir carros associados à minha conta e também desejo encerrar permanentemente minha conta de usuário quando necessário.

4) **Obtenção de Carros e Usuários:**
   - Como usuário autenticado, desejo obter informações detalhadas sobre os carros associados à minha conta e visualizar dados do meu próprio perfil de usuário.

5) **Autenticação de Usuários:**
   - Como usuário registrado, desejo autenticar-me na aplicação utilizando credenciais válidas para acessar recursos protegidos.

6) **Interface do Utilizador do Sistema (Front-end) - Angular:**
   - Como usuário da aplicação, desejo utilizar uma interface do usuário intuitiva e amigável para interagir com os recursos do sistema, visualizar e editar informações dos carros e do meu perfil de usuário. Desenvolvida em Angular.

## Solução

1) Arquitetura em Camadas: O projeto parece seguir uma arquitetura em camadas, dividindo as responsabilidades entre os controladores, serviços e repositórios. Isso ajuda na organização e manutenção do código.

2) Injeção de Dependência: A utilização de anotações como @Autowired sugere o uso de injeção de dependência, promovendo um baixo acoplamento e facilitando a substituição de implementações.

3) RESTful API: Os endpoints no controlador (@RestController) indicam uma abordagem RESTful para a exposição dos serviços, seguindo boas práticas para operações CRUD.

4) DTO (Data Transfer Object): A utilização de classes como UserInput, UserOutput, CarInput, CarOutput sugere o uso de DTOs para transferência de dados entre camadas. Isso é uma boa prática para evitar vazamento de informações da camada de persistência.

5) Tratamento de Exceções Global: A implementação de um handler global para exceções (@ExceptionHandler) ajuda a centralizar o tratamento de erros e fornece respostas consistentes para o cliente.

6) Segurança: O uso de JWT para autenticação e autorização é uma boa prática de segurança em aplicações web.

7) Padrões REST: A estrutura dos seus endpoints (/api/cars, /api/users, etc.) segue convenções REST, o que é uma prática positiva.

8) Spring Framework: A utilização do Spring Framework fornece um conjunto abrangente de ferramentas e funcionalidades para desenvolvimento de aplicativos Java, facilitando o desenvolvimento e a manutenção do código.

9) Lombok: O uso de Lombok com anotações como @Getter, @Setter, @AllArgsConstructor, etc., ajuda a reduzir a verbosidade do código, mantendo a clareza.

10) Tratamento de Data e Hora: A conversão entre tipos de data (LocalDateTime e Date) sugere considerações cuidadosas ao lidar com diferentes representações de tempo.

### Justificativas e Defesa Técnica

-- Ao embarcar na jornada de desenvolvimento do SystemCar, foram feitas diversas escolhas tecnológicas com o objetivo de criar uma aplicação eficiente, segura e de fácil manutenção. Este texto explora algumas dessas escolhas, destacando a versatilidade e modernidade do Angular, a eficiência trazida por bibliotecas como Lombok e Spring Security, e as razões por trás da escolha da versão 8 do Java.

1) Arquitetura em Camadas:
O SystemCar adota uma arquitetura em camadas, distribuindo as responsabilidades entre controladores, serviços e repositórios. Essa abordagem organizacional facilita a manutenção e escalabilidade do código.

2) Injeção de Dependência:
O projeto utiliza anotações como @Autowired, indicando a prática de injeção de dependência. Esse padrão contribui para um baixo acoplamento entre componentes e possibilita uma fácil substituição de implementações.

3) RESTful API:
A exposição de serviços através de endpoints em controladores (@RestController) reflete a adoção de uma abordagem RESTful. Essa prática segue padrões CRUD e contribui para uma interface consistente e intuitiva.

4) DTO (Data Transfer Object):
O uso de classes como UserInput, UserOutput, CarInput e CarOutput sugere a aplicação de DTOs para transferência de dados entre camadas. Essa prática evita vazamento de informações e contribui para a segurança do sistema.

5) Tratamento de Exceções Global:
A implementação de um handler global para exceções (@ExceptionHandler) centraliza o tratamento de erros, proporcionando respostas coerentes ao cliente. Isso simplifica a manutenção e aprimora a experiência do usuário.

6) Segurança:
A segurança é fortalecida pelo uso de JWT (JSON Web Token) para autenticação e autorização. Essa prática moderna garante a integridade das comunicações e reforça a proteção dos dados sensíveis.

7) Padrões REST:
A estrutura de endpoints (/api/cars, /api/users, etc.) segue os padrões REST, promovendo uma navegação lógica e consistente na API. Isso facilita a compreensão e o uso por parte dos desenvolvedores.

8) Spring Framework:
A escolha do Spring Framework oferece um conjunto completo de recursos para o desenvolvimento Java. Essa ferramenta robusta simplifica tarefas complexas, impulsionando a eficiência do desenvolvimento e a manutenção do código.

9) Lombok:
O uso de Lombok com anotações como @Getter, @Setter, @AllArgsConstructor reduz a verbosidade do código. Essa prática mantém a clareza do código, eliminando a necessidade de escrever boilerplate.

10) Tratamento de Data e Hora:
A manipulação eficaz de diferentes representações de tempo, como LocalDateTime e Date, demonstra uma abordagem cuidadosa para lidar com dados temporais. Isso garante consistência nas operações relacionadas a datas e horas.

### Tecnologia Front-end

- **Angular:**
  - Framework utilizado para o desenvolvimento do front-end, proporcionando uma interface de usuário dinâmica e interativa.

#### Frameworks Utilizados

- **Spring Framework:**
  - Amplamente adotado, facilita a integração e suporte para o desenvolvimento Java robusto.

- **JPA/Hibernate e H2:**
  - Facilitam a persistência de dados e fornecem uma solução leve em memória para desenvolvimento.

#### Segurança

- **JWT (JSON Web Token):**
  - Fornece uma camada adicional de segurança para autenticação.

- **Criptografia de Senhas:**
  - Protege dados sensíveis dos usuários.

### Testes Unitários

- Implementados utilizando JUnit para garantir robustez do código e detecção precoce de problemas.

#### Documentação

- Javadoc utilizado para gerar documentação clara e concisa do código-fonte.

#### Pipeline de Build e Testes

- Automação de build e testes para facilitar a integração contínua.

#### Swagger

- Simplifica a documentação da API e facilita a interação com os endpoints.

#### Repositório Git

- Repositório público no GitHub para transparência e colaboração eficiente.

### Execução do Projeto

1. **Requisitos:**
   - Java 8 instalado.
   - Maven instalado.
   - Banco de dados H2 configurado.

2. **Build e Execução:**
   - Clone o repositório do GitHub.
   - Configure as propriedades do banco de dados em `application.properties`.
   - Execute `mvn clean install` para realizar o build.
   - Execute o aplicativo resultante.

3. **Testes:**
   - Execute `mvn test` para executar os testes unitários.

4. **Documentação da API:**
   - Acesse a documentação da API gerada pelo Swagger em [XXXXXXX].

## Contribuição

Sinta-se à vontade para contribuir com melhorias, correções ou implementações. Abra uma issue para discussão antes de iniciar qualquer trabalho.

## Licença

Este projeto é licenciado apenas para utilização no processo de admissão para a vaga proposta.

---

**Desenvolvido por Elias Barreto Ferreira Filho**
