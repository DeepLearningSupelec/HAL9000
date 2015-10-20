
public class BackPropagation extends LearningAlgorithm {


	private void calculateNeuronDiff(AbstractNeuron n){

		double gradient;

		//weightedSum is calculated for the intermediate and output neuron
		double weightedSum = 0;
		if(n instanceof Neuron) {
			for (Synapse s : n.getInputSynapses()) {
				weightedSum = weightedSum + s.getWeight()*s.getInputNeuron().getOutput() ;
			}
			weightedSum = weightedSum - n.bias;

		}
		
		// The operations differ whether the neuron is an OutputNeuron or another neuron

		if (n instanceof OutputNeuron ){
			double error = expectedOutput - n.output;
			n.setNeuronDiff(n.activationFunction.applyDerivative(weightedSum)*error); // delta = f'(input)*e

		}

		if (n instanceof Intermediate) {
			//weighted sum of the output is calculated
			double weightedErrorOutput = 0 ;
			for (Synapse s : n.getOutputSynapses()) {
				weightedErrorOutput = weightedErrorOutput +s.getWeight()*s.getOutputNeuron().getNeuronDiff() ;
			}
			n.setNeuronDiff (n.activationFunction.applyDerivative(weightedSum)*weightedErrorOutput); // delta = f'(input)* sum ( gradient next Neuron * weight linked synapse) 
		}

	}



	private void incrementWeightsDiff(Synapse s){
		if(s.getOutputNeuron() instanceof OutputNeuron){
			s.setWeightDiff(s.getWeightDiff()+s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		}
		else{
			s.setWeightDiff(s.getWeightDiff()+s.getInputNeuron().getOutput()*s.getOutputNeuron().getNeuronDiff());
		}
	}



	private void incrementWeights(Synapse s, double a ){
		s.setWeight(s.getWeight()+ a*s.getWeightDiff());
	}


	public void launch(NeuralNetwork N, double a){ //BackPropagation : learning algorithm; a is the learning rate

		if (N instanceof Perceptron){
			
			for (AbstractNeuron n : N.outputNeurons ){ //Beginning with the end of the Neural Network

				if(n instanceof OutputNeuron){  // all NeuronDiff are calculated

					calculateNeuronDiff(n);

					for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated  between the output layer and the hidden layer
						incrementWeightsDiff(s);
					}
				}
			}


			for (AbstractNeuron n : N.intermediateNeurons ){

				if(n instanceof IntermediateNeuron ){ //NeuronDiff are calculated
					calculateNeuronDiff(n);

					for(Synapse s : n.getInputSynapses()){ // Weight gradient are updated between the input layers and the hidden layers
						incrementWeightsDiff(s);
					}
				}
			}

			for(AbstractNeuron n : N.outputNeurons ){   //Synapse's weight are updated
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}

			for(AbstractNeuron n : N.intermediateNeurons ){ //Synapse's weight are updated
				for(Synapse s : n.getInputSynapses()){
					incrementWeights(s,a);
				}
			}
		}
	}


}
