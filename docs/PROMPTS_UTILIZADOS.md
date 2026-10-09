# Prompts utilizados na modificação da aplicação

## Prompt 1 - solicitação principal

> Utilizar IA Generativa para aplicar o VERTICAL SLICE e CLEAN ARCHITECTURE e SOLID no backend da aplicação TDE 2. Utilizar o código da aplicação que está sendo criada no TDE 2. Em documento PDF entregar: no arquivo deve constar o nome dos alunos que auxiliou na tarefa, independente de ser atividade em grupo; descreva o que cada aluno realizou; informar o GitHub do projeto em uma nova branch; informar todos os prompts utilizados para modificar a aplicação; entregar diagrama de classes e componentes do backend da aplicação em Vertical Slice e Clean Architecture e SOLID, gerar em markdown e imagem. Alunos: Lucas Mockel Roussenq, Vinícius Reis, Erik Carvalho e Mateus Burlamaqui.

## Prompt 2 - contexto do projeto

> codex://threads/01a0cc7d-99d1-7173-9b7c-1bd536ce7725. Se trata do link acima. Vamos fazer a documentação da parte 2 e desenvolver o trabalho de agora.

## Prompts de trabalho derivados

Estes prompts registram as instruções técnicas usadas para decompor a solicitação durante a execução, sem expor instruções internas da ferramenta:

1. **Análise do código:** “Analise a fundação do backend NexFleet e identifique o menor fluxo executável que demonstre Vertical Slice, Clean Architecture e SOLID sem antecipar módulos dependentes.”
2. **Implementação:** “Implemente a Sprint 1 com login JWT e criação de usuários por administrador, mantendo domínio e aplicação independentes de Spring/JPA.”
3. **Segurança:** “Use BCrypt para senhas, JWT stateless, administrador inicial configurado por ambiente e autorização do endpoint de usuários no backend.”
4. **Persistência:** “Implemente PostgreSQL e Flyway com e-mail normalizado e único, sem expor entidades JPA como resposta HTTP.”
5. **Qualidade:** “Crie testes unitários dos casos de uso e testes ArchUnit para dependências, frameworks no domínio e ciclos entre módulos.”
6. **Diagramas:** “Gere diagramas de classes e componentes que correspondam às classes e dependências realmente entregues, em Mermaid/Markdown e PNG.”
7. **Documentação:** “Atualize a Parte 2 para refletir o código entregue, registre SOLID, limitações, evidências de teste, alunos e branch GitHub.”

