package jd.persistence.dto;

/**
 * Created by eduardom on 10/6/16.
 */
public class ApplyGiftcardDTO {
    private long jdcard;
    private String claimcode;


    public ApplyGiftcardDTO() {
    }

    public ApplyGiftcardDTO(long jdcard, String claimcode) {
        this.jdcard = jdcard;
        this.claimcode = claimcode;
    }

    public long getJdcard() {
        return jdcard;
    }

    public void setJdcard(long jdcard) {
        this.jdcard = jdcard;
    }

    public String getClaimcode() {
        return claimcode;
    }

    public void setClaimcode(String claimcode) {
        this.claimcode = claimcode;
    }
}
