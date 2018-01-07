@echo off

cd /d %~dp0
echo %1

set a=3
if %1==Status (
git status > result.txt
) else if %1==Add (
git add %2 > result.txt
) else if %1==Commit (
git commit -m %2 > result.txt
) else if %1==Push (
git push > result.txt
)
