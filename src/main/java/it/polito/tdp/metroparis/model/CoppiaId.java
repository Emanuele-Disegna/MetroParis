package it.polito.tdp.metroparis.model;

public class CoppiaId {
	int idPartenza;
	int idArrivo;
	
	public CoppiaId(int idPartenza, int idArrivo) {
		super();
		this.idPartenza = idPartenza;
		this.idArrivo = idArrivo;
	}

	public int getIdPartenza() {
		return idPartenza;
	}

	public void setIdPartenza(int idPartenza) {
		this.idPartenza = idPartenza;
	}

	public int getIdArrivo() {
		return idArrivo;
	}

	public void setIdArrivo(int idArrivo) {
		this.idArrivo = idArrivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idArrivo;
		result = prime * result + idPartenza;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaId other = (CoppiaId) obj;
		if (idArrivo != other.idArrivo)
			return false;
		if (idPartenza != other.idPartenza)
			return false;
		return true;
	}
	
	
}
