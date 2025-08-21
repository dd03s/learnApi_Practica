package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private  String cloudName;
    private  String apiKey;
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
// crea un onjeto
        Dotenv dontenv = Dotenv.load();

        //Crear un map para almacenar la configuracion
        Map<String, String > config = new HashMap<>();

        config.put("cloud_name", dontenv.get("CLOUDINARY_CLOUD_NAME"));
        config.put("api_key", dontenv.get("CLOUDINARY_API_KEY"));
        config.put("api_secret", dontenv.get("CLOUDINARY_API_SECRET"));


        //Retornar una nueva instancia de cloudinary con la configuracion cargada
        return new Cloudinary(config);
    }
}
