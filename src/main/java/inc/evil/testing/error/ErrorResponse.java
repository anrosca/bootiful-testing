package inc.evil.testing.error;

import java.util.List;
import java.util.Objects;

public class ErrorResponse {
	private String path;

	private List<String> messages;

	public ErrorResponse() {
	}

	public ErrorResponse(ErrorResponseBuilder builder) {
		this.path = builder.path;
		this.messages = builder.messages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ErrorResponse that = (ErrorResponse) o;
		return Objects.equals(path, that.path) && Objects.equals(messages, that.messages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, messages);
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"path='" + path + '\'' +
				", messages=" + messages +
				'}';
	}

	public static ErrorResponseBuilder builder() {
		return new ErrorResponseBuilder();
	}

	public static class ErrorResponseBuilder {
		private String path;

		private List<String> messages;

		public ErrorResponseBuilder path(String path) {
			this.path = path;
			return this;
		}

		public ErrorResponseBuilder messages(List<String> messages) {
			this.messages = messages;
			return this;
		}

		public ErrorResponse build() {
			return new ErrorResponse(this);
		}
	}
}
