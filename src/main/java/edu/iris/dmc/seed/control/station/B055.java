package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public class B055 extends AbstractResponseBlockette implements OverFlowBlockette{

   private List<Response> responses = new ArrayList<>();

   public B055() {
      super(55, "Response List Blockette");

   }

   public List<Response> getResponses() {
      return responses;
   }

   public void setResponses(List<Response> responses) {
      this.responses = responses;
   }

   public void add(Response response) {
      this.responses.add(response);
   }

   public class Response {
	      private Double frequency;
	      private Double amplitude;
	      private Double amplitudeError;
	      private Double phaaeAngle;
	      private Double phaseError;

	      public Response() {
	      }

	      public Response(Double frequency, Double amplitude, Double amplitudeError, Double phaaeAngle,
	            Double phaseError) {
	         super();
	         this.frequency = frequency;
	         this.amplitude = amplitude;
	         this.amplitudeError = amplitudeError;
	         this.phaaeAngle = phaaeAngle;
	         this.phaseError = phaseError;
	      }

	      // Add a size function here

	      public Double getFrequency() {
	         return frequency;
	      }

	      public void setFrequency(Double frequency) {
	         this.frequency = frequency;
	      }

	      public Double getAmplitude() {
	         return amplitude;
	      }

	      public void setAmplitude(Double amplitude) {
	         this.amplitude = amplitude;
	      }

	      public Double getAmplitudeError() {
	         return amplitudeError;
	      }

	      public void setAmplitudeError(Double amplitudeError) {
	         this.amplitudeError = amplitudeError;
	      }

	      public Double getPhaaeAngle() {
	         return phaaeAngle;
	      }

	      public void setPhaaeAngle(Double phaaeAngle) {
	         this.phaaeAngle = phaaeAngle;
	      }

	      public Double getPhaseError() {
	         return phaseError;
	      }

	      public void setPhaseError(Double phaseError) {
	         this.phaseError = phaseError;
	      }

	   }


	@Override
	public boolean isOverFlown() throws SeedException {
		if (super.isOverFlown()) {
			return true;
		}

		if ((this.responses.size()) > 14) {
			return true;
		}
		return false;
	}


   @Override
   public OverFlowBlockette merge(OverFlowBlockette b) throws SeedException {
      Objects.requireNonNull(b, "Blockette cannot be null");
      if (!(b instanceof B055)) {
         throw new SeedException("Cannot merge with " + this.type + ".");
      }
      B055 m = (B055) b;
      this.getResponses().addAll(m.getResponses());
      return this;
   }


   @Override
   public List<Blockette> split() throws SeedException {
      List<Blockette> list = new ArrayList<>();
      B055 b055 = null;
      if (this.getSize() < this.getLength()) {
         list.add(this);
         return list;
      }

      for (Response n : this.responses) {
    	  //Integer value 62 controls the split byte for overflow blockette  
          if (b055 == null || b055.getSize() + 61 > this.getLength()) {
             b055 = new B055();
             b055.setId(this.id);
             b055.setStageSequence(this.getStageSequence());
             b055.setSignalInputUnit(this.getSignalInputUnit());
             b055.setSignalOutputUnit(this.getSignalOutputUnit());
             b055.setStageSequence(this.getStageSequence());
             list.add(b055);
         }
         b055.add(n);
      }
      return list;
   }

   @Override
   public String toSeedString() {

      SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
      builder.append(this.getStageSequence(), 2);
      builder.append(this.getSignalInputUnit(), 3);
      builder.append(this.getSignalOutputUnit(), 3);

      builder.append(this.responses.size(), 4);

      for (Response response : this.responses) {
         builder.append(response.getFrequency(), "-0.00000E-00", 12);
         builder.append(response.getAmplitude(), "-0.00000E-00", 12);
         builder.append(response.getAmplitudeError(), "-0.00000E-00", 12);
         builder.append(response.getPhaaeAngle(), "-0.00000E-00", 12);
         builder.append(response.getPhaseError(), "-0.00000E-00", 12);
      }
      // The length of the blocks it off
      builder.replace(3, 7, builder.length(), "####");
      return builder.toString();
   }
}