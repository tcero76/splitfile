.PHONY: test

test:
	@while true; do \
		echo "Ejecutando test..."; \
		 ./gradlew test --tests com.example.splitfile.controller.FileUploadTest; \
		sleep 5; \
	done
