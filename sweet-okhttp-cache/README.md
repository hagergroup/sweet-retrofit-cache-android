# Sync the project with the upstream repository

if needed : 
```
git remote add upstream https://github.com/apollographql/apollo-android
```

```
git checkout -b main
git fetch upstream
git merge upstream/main
git reset --hard {tag commit id}
git push --force
git push --tags
```

Then merge the master branch into the okhttp-cache one
