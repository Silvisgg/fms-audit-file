package silgar.store.controller;

import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import silgar.store.exception.ErrorMessage;
import silgar.store.service.IStoreService;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
public class StoreController {

    private final IStoreService iStoreService;

    @Autowired
    public StoreController(IStoreService iStoreService){
        this.iStoreService = iStoreService;
    }

    /*
    Store a file to the Storage
     */
    @PostMapping(path = "/file/store-s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Mono<ResponseEntity> handleSingleFileStore(@RequestParam("file") MultipartFile inbound){

        log.info("Entering handleFileStore. ");

        return Mono.just(inbound)
                .doOnNext(iStoreService::store)//actua como una sonda y reacciona. envia y recibe info pero no la transforma
                .map( file -> {  // transformacion plana de un obj a otro obj - flatmap a varios objetos
                    log.info("Entering map file");

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Custom-Header", "StoreController");
                    ResponseEntity responseEntity = ResponseEntity.ok()
                            .headers(headers)
                            .body("File " + file.getOriginalFilename() + " stored!");
                        return responseEntity;
                })
                .onErrorResume(exc -> {
                    log.info("Entering onErrorResume. ");
                    return throwExc(exc);

                });//convertir apiExcephandler a servicio
                         // en caso de error no se detiene el flujo
    }


    /*
    Check if the application is running
     */
    @GetMapping(value = "/health")
    @ResponseBody
    public ResponseEntity<String> status() {

        return ResponseEntity.ok().body("Store Application is running...");
    }


    /*
Check if the application is running
 */
    @GetMapping(path = "/healthmono/{cadena}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity> statusMono(@PathVariable("cadena") String cadena) {

        return Mono.just(cadena)
                .map( palabra -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Custom-Header", "HealthController");
                    ResponseEntity responseEntity = ResponseEntity.ok()
                            .headers(headers)
                            .body("Store Application is running... Mono controller. Palabra: " + palabra);
                    return responseEntity;
                })
                .onErrorResume(exc -> throwExc(exc));
    }


    private Mono<ResponseEntity> throwExc (Throwable exc){
    HttpHeaders headers = new HttpHeaders();
                            headers.add("Custom-Header", "Controller advice");

    ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            exc.getMessage(),
            exc.getStackTrace().toString(),
            "");
log.info("ERRORRR: "+errorMessage.getMessage());
    return Mono.just(new ResponseEntity (errorMessage,headers,HttpStatus.INTERNAL_SERVER_ERROR));

    }
}
