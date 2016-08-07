package melerospaw.memoryutil;

/**
     * Contains the result of method call from {@link MemoryUtils} methods.<br /><br />
     * <b>Fields:</b><br/>
     * <ul>
     * <li>{@code successful}: tells whether the method call has been successful or not.</li>
     * <li>{@code result}: the object resulting from the method call. If you save something,
     * you'll get the {@code File} pointing to the file where that something was saved.</li>
     * <li>{@code message}: a {@code String} containing the reason why the method called was not
     * successful.</li>
     * </ul>
     *
     * @param <T> The type of the object that will result from the method call. If the call results
     *            in nothing, no {@code T} parameter needs to be specified. For example, from method
     *            {@link MemoryUtils#deleteFile(Path, boolean)} nothing is returned.
     */
    public class Result<T> {

        private boolean successful;
        private T result;
        private String message;

        /**
         * Tells whether the method call has been successuful or not. This is the most
         * straightforward way to know, rather than calling {@code if (result.getResult() != null},
         * since sometimes nothing is returned from the latter method.
         *
         * @return {@code true} if the method call has been successful.
         */
        public boolean isSuccessful() {
            return successful;
        }

        void setSuccessful(boolean successful) {
            this.successful = successful;
        }


        /**
         * Returns the result of the call to a {@link MemoryUtils}' method.
         *
         * @return <ul>
         * <li>When you save something, this method returns a {@code File} object pointing to
         * the file where that something was called.</li>
         * <li>When you want to retrieve a text or an object, this method returns that text or
         * the object casted to its class type.</li>
         * <li>When the method you call results in nothing, there is no need to instantiate
         * {@code Result} using the diamond operator and calls to this method returns
         * {@code null}.</li>
         * <li>When the method didn't execute successfully, this method returns {@code null}.</li>
         * </ul>
         */
        public T getResult() {
            return result;
        }

        void setResult(T result) {
            this.result = result;
        }


        /**
         * When an error occurs during the execution of the {@link MemoryUtils} method, this method
         * returns a{@code String} with the error outcome. Errors and their stack traces are also
         * displayed in the logcat.
         *
         * @return A String containing the error.
         */
        public String getMessage() {
            return message;
        }

        void setMessage(String message) {
            this.message = message;
        }
    }