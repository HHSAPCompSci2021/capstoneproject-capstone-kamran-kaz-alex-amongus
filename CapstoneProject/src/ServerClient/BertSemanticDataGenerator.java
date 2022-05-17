package ServerClient;

import java.util.Random;

public class BertSemanticDataGenerator {

	private String[] sentence_pairs;
	private boolean shuffle, include_targets;
	private int batch_size;
	private int[] indexes;
	private Tokenizer tokenizer;

	public BertSemanticDataGenerator(String[] sentence_pairs, int batch_size, boolean shuffle, boolean include_targets) {
		this.sentence_pairs = sentence_pairs;
		this.shuffle = shuffle;
		this.batch_size = batch_size;
		this.include_targets = include_targets;
		this.tokenizer = transformers.BertTokenizer.from_pretrained("bert-base-uncased", true);
		this.indexes = np.arange(this.sentence_pairs.length);
		this.onEpochEnd();
	}

	private void onEpochEnd() {
		if(shuffle) {
			Random r = new Random();
			r.nextInt(42).shuffle(this.indexes);
		}
	}
}
