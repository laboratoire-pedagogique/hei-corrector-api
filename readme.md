# HEI CORRECTOR API

## 1.0

Pour utiliser l'API, il faudra:

1) assigner les valeurs requises aux env **db_url**, **db_username** et **db_password**.
2) créer un dossier "**output**".
3) mettre les sujets à tester dans le dossier **srcset**/**session_source**/**(nom des dossiers en ref étudiant)** (eg: **srcset/test/** avec les dossiers des tests pour les h4 à l'intérieur)
4) s'assurer que les **package.json** dans chaque sous-dossiers à tester peuvent lancer les tests. (voir modifications dans **h4-exam**)
5) utiliser le format suivant pour les tests:
 
```sh
    describe('KATA : kata_name (pt:x)') où x est le point total
    it('test_description (p:y)') où y est le point pour le test
```


> Pour le moment, seulement les test mocha sont faisables. 
> En effet l'API vérifie d'abord si les dépendences ont été installés avant de lancer les tests. 
> Pour un projet maven ou gradle ca peut prendre du temps selon la connexion même si les différents dossiers sont traités dans des threads différents . 

6) collection postman disponible dans le dossier **postman**.