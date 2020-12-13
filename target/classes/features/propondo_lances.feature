# language: pt
Funcionalidade: Propondo Lances

	Cenario: Propondo um único lance válido
  	Dado um lance de 50.00 reais do usuário 'fulano'
  	Quando propõe ao leilão
  	Entao o lance é aceito
  	
  Cenario: Propondo vários lances válidos
  	Dado um lance de 10.00 reais do usuário 'fulano'
  	E um lance de 15.00 do usuário 'beltrano'
  	Quando propõe ao leilão
  	Entao os lance são aceitos
  	
  Esquema do Cenario: Propondo um único lance inválido
    Dado um lance inválido de <valor> reais do usuário '<usuario>'
  	Quando propõe ao leilão
  	Entao o lance não é aceito
  	
  	Exemplos:
  	|valor  | usuario  |
  	|   0.00| fulano   | 
  	|-100.00| beltrano |
  	
  Cenario: Propondo uma sequência de lances
  	Dado dois lances
  	     |valor  | usuario  |
		  	 |  15.00| fulano   | 
		  	 |  20.00| fulano   |
  	Quando propõe ao leilão
  	Entao o segundo lance não é aceito