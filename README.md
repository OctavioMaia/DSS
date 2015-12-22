# Projeto DSS 2015/2016

Coisas a considerar:
- Possibilidade de "esconder" partidos e coligações.
- Acho que ListaPR e CandidatoPR podem juntar, discutir isso

# Cenas do Octávio:

- [ ] Interfaces
  - [x] Login
    - [x] Butão confirmar
    - [x] Butão sair
    - [x] Parse numero identificacao
    - [x] Parse password
  - [x] GerirPR
    - [x] Data Inicio + parse
    - [x] Data nascimento + parse
    - [x] Nome, naturalidade,residencia,profissao,bi, foto + parse
    - [x] Apagar informacoes
    - [x] Adicionar ao SGE 
  - [ ] MainEleitor
    - [x] Nome eleitor
    - [x] Tipo eleicao
    - [x] Data eleicao
    - [x] Votar
    - [ ] Historico eleicoes
    - [x] Ver resultados
  - [ ] ResultadosAR
    - [x] Resultados globais
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [x] Resultados circulo
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [ ] Implementar selecao de colunas nas tabelas pra mostrar informacao
  - [ ] ResultadosPR
    - [x] Resultados globais
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [x] Resultados circulo
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [ ] Implementar selecao de colunas nas tabelas pra mostrar informacao
  - [x] Votar
    - [x] Parse nome eleitor
    - [ ] Listagem das listas/candidatos
    - [ ] Imagem
    - [x] Limpar
    - [x] Votar
  - [x] Inserir caderno de recenseamento
    - [x] Path parse
    - [x] Ler ficheiro
    - [x] Povoar table consoante circulo
    - [ ] Escrever pra DB (funciona mas crasha ai a 60%)
  - [x] Criar Eleição
    - [x] Selecionar tipo
    - [x] Selecionar data
    - [x] Avançar pra eleicao respetiva

# Cenas dos DAO's:

- [ ] Feitas/Testas
  - [X] AdminDAO
  - [X] PartidosDAO
  - [X] CirculoInfoDAO
  - [X] Connector
  - [X] EleiçãoARDAO
  - [ ] EleiçãoPRDAO
  - [X] EleitoresDAO
  - [X] ListaPRDAO
  - [ ] ResultadoCirculoPRDAO
  - [X] CirculoInfoDAO
  - [X] ColigaçãoDAO
  - [X] CirculoDAO
  - [ ] ResultadoCirculoARDAO
  - [ ] ListaARDAO
