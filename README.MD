# Your backend API

## Set up 
1. Copie-colle le .env.sample en .env
2. Ajoute tes variable a toi
3. Fait un `docker-compose up`
4. Rajoute le pluggin : https://plugins.jetbrains.com/plugin/7861-envfile
5. <img src="img-readme/img.png">
6. <img src="img-readme/img_1.png">
7. <img src="img-readme/img_2.png">
8. Ajoute la BDD, c'est une postgres
   <img src="img-readme/img_mine1.png">
   test la connection à ta BDD
   <img src="img-readme/img_mine2.png">
9. Run la BDD et ajouter les infos
   <img src="img-readme/img_mine3.png">
   <img src="img-readme/img_mine4.png">
10. Modifier le application.properties dans les fichier ressources dans le main
   il faut remplacer les `${DATABASE_PASSWORD}`, `${DATABASE_USER}`, `${DATABASE_NAME}`, par vos valeurs à vous.
    <img src="img-readme/img_mine5.png">
11. Vous pouvez tester le back directement en faisant run le cli, dans src/main/cli

