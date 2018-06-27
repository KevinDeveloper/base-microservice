生成密钥：
Kevin123

keytool -genkeypair -alias Kevin-jwt -validity 3650 -keyalg RSA -dname "CN=jwt,OU=jwt, O=jwt, L=zurich, S=CH" -keypass Kevin123 -keystore Kevin-jwt.jks -storepass Kevin123

生成公钥：
keytool -list -rfc --keystore Kevin-jwt.jks | openssl x509 -inform pem -pubkey