.PHONY: test link package clean

link:
	@jlink --output jre-miapp --add-modules java.base,java.logging,java.desktop,java.naming,java.sql --strip-debug --no-header-files --no-man-pages

package:
	@jpackage --name MiApp --input ./build/libs/ --main-jar splitfile-0.0.1-SNAPSHOT.jar --dest ./paquete/ --runtime-image ./jre-miapp --type app-image --main-class org.springframework.boot.loader.JarLauncher

clean:
	@rm -rf paquete/ jre-miapp/

test:
	@while true; do \
		echo "Ejecutando test..."; \
		 ./gradlew test --tests com.example.splitfile.controller.FileUploadTest; \
		sleep 5; \
	done
