alter table mu_macellazioni
add id_veterinario1_pm integer,
add id_veterinario2_pm integer,
add id_veterinario3_pm integer




	@Column(columnName = "id_veterinario1_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario1Pm = -1;
	@Column(columnName = "id_veterinario2_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario2Pm = -1;
	@Column(columnName = "id_veterinario3_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario3Pm = -1;