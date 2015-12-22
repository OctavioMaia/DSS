package Comparator;

import java.util.Comparator;

import Business.VotavelVotos;

public class ComparatorVotavelVotos implements Comparator<VotavelVotos> {

	public int compare(VotavelVotos lv1, VotavelVotos lv2) {
		if (lv1.getVotos() > lv2.getVotos())
			return 1;
		if (lv1.getVotos() < lv2.getVotos()) {
			return -1;
		}
		return lv1.toString().compareTo(lv2.toString());
	}
}
