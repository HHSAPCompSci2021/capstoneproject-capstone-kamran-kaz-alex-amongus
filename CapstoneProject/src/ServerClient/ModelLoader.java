package ServerClient;

public class ModelLoader {
	
	private String path = "";
	
	/**
	 * loads the model from the specified path
	 * @param path The path to the directory containing the model
	 */
	public ModelLoader(String ModelPath) {
		path = ModelPath;
		
	}

	/**
	 * 
	 * @return The path to the directory containing the model
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @param path The path to the model's directory 
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
