@echo off

cd %2
echo %1
echo %2

set a=3
if %1==Status (
git status > result.txt
) else if %1==Add (
git add %3 > result.txt
) else if %1==Commit (
git commit -m %3 > result.txt
) else if %1==Push (
git push origin %3 > result.txt
)else if %1==Pull (
git pull origin %3 > result.txt
)
exit