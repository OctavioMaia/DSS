package Business;

public class VotavelVotos {
	private Votavel votavel;
	private int votos;
	
	public VotavelVotos(Votavel votavel, int votos) {
		this.votavel = votavel;
		this.votos = votos;
	}
	public Votavel getVotavel() {
		return votavel;
	}
	public void setVotavel(Votavel votavel) {
		this.votavel = votavel;
	}
	public int getVotos() {
		return votos;
	}
	public void setVotos(int votos) {
		this.votos = votos;
	}
}
