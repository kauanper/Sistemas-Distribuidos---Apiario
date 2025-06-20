# Gestão de Apiário

Sistema de gerenciamento de colmeias e aplicação distribuída de votação com tempo restrito.

## Descrição

O sistema Gestão de Apiário permite que apicultores gerenciem suas colmeias por meio de requisições ao servidor. É possível:

- Adicionar novas colmeias;
- Visualizar colmeias já cadastradas;
- Inserir abelhas em cada colmeia, respeitando as seguintes regras:
  - Quantas abelhas operárias forem desejadas;
  - Apenas uma rainha por colmeia.
- Deletar uma colmeia cadastrada

A classe `Apicultor` é utilizada como o principal POJO (Plain Old Java Object) de entrada, saída e serialização dos dados. Ela centraliza a representação das entidades do sistema, com exceção dos serviços.

O servidor executa os serviços solicitados pelo apicultor, garantindo a lógica e integridade da aplicação. Interfaces de validação reforçam a consistência dos dados nas principais operações do sistema.

As interações entre o cliente e o servidor para o gerenciamento de colmeias são realizadas por meio de **Java RMI (Remote Method Invocation)**, permitindo chamadas remotas de métodos com facilidade e segurança.

## Funcionalidades

### Módulo de Gestão de Colmeias

- Gerenciamento centralizado pelo apicultor;
- Operações de criação, listagem, adição de abelhas na colemia e deletar colmeia;
- Controle das abelhas em cada colmeia, com restrição de apenas uma rainha;
- Comunicação entre cliente e servidor feita com sockets baseado em TCP.
- Também possui comunicação entre cliente e servidor feita com Java RMI.

### Módulo de Votação Distribuída (com tempo limitado)

- Login de eleitores via TCP (unicast);
- Recebimento da lista de candidatos;
- Envio de voto dentro de um intervalo de tempo determinado;
- Encerramento automático da votação após o tempo expirar;
- Contagem dos votos e cálculo de porcentagens;
- Determinação do vencedor;
- Envio de notificações do administrador via UDP (multicast), atingindo todos os eleitores ao mesmo tempo.

## Tecnologias Utilizadas

- Java RMI e Sockets
- Programação orientada a objetos (POO)
- TCP e UDP
- Multi-threading no servidor de votação

## Autores

Desenvolvido por Francisco Kauan Pereira Cavalcante(554089) e Juan Pablo Rufino Mesquita(509982).
