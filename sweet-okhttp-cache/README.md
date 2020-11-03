[![Build status](https://dev.azure.com/hagerdigitalfactory/Digital%20Factory%20Documentation/_apis/build/status/sweet-okhttp-cache-android/sweet-okhttp-android-release)](https://dev.azure.com/hagerdigitalfactory/Digital%20Factory%20Documentation/_build/latest?definitionId=116)  [ ![Download](https://api.bintray.com/packages/hagergroup/Maven/sweet-okhttp-cache/images/download.svg) ](https://bintray.com/hagergroup/Maven/sweet-okhttp-cache/_latestVersion)

# Sync the project with the upstream repository

if needed: 
```
git remote add upstream https://github.com/apollographql/apollo-android
```

Then: 
```
git checkout main
git fetch upstream
git merge upstream/main
git reset --hard {tag commit id}
git push --force
git push --tags
```

Finally merge the `main` branch into the `sweet-okhttp-cache` one

# TODO

* Add the possibility to clean-up the cache
