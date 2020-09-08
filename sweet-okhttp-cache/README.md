Update the project to the lastest tags :

if needed : 
git remote add upstream https://github.com/apollographql/apollo-android

git checkout -b origin/master
git fetch upstream
git merge upstream/master
git reset --hard {tag commit id}
git push --force
git push --tags

then merge the master branch to the retrofit-cache one