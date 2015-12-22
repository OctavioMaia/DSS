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
    - [x] Nome eleitor (falta metodo no sge)
    - [x] Tipo eleicao
    - [x] Data eleicao
    - [ ] Votar (butão nao faz nada ainda)
    - [ ] Historico eleicoes
    - [ ] Ver resultados
  - [ ] ResultadosAR
    - [x] Resultados globais
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [x] Resultados circulo
      - [x] Calculo votos brancos nulos e abstencao 
      - [x] Calculo percentagens votos brancos nulos e abstencao
    - [ ] Implementar selecao de colunas nas tabelas pra mostrar informacao
  - [ ] ResultadosPR
    - [ ] Resultados globais
      - [ ] Calculo votos brancos nulos e abstencao 
      - [ ] Calculo percentagens votos brancos nulos e abstencao
    - [ ] Resultados circulo
      - [ ] Calculo votos brancos nulos e abstencao 
      - [ ] Calculo percentagens votos brancos nulos e abstencao
    - [ ] Implementar selecao de colunas nas tabelas pra mostrar informacao
  - [ ] Votar

# Cenas dos DAO's:

- [ ] Por Fazer/A fazer
  - [ ] EleiçãoARDAO
  - [ ] ListaARDAO

- [ ] Feitas/Testas
  - [X] AdminDAO
  - [ ] PartidosDAO
  - [X] CirculoInfoDAO
  - [X] Connector
  - [ ] EleiçãoPRDAO
  - [X] EleitoresDAO
  - [ ] ListaPRDAO
  - [ ] ResultadoCirculoPRDAO
  - [X] CirculoInfoDAO
  - [ ] ColigaçãoDAO
  - [X] CirculoDAO
  - [ ] ResultadoCirculoARDAO
