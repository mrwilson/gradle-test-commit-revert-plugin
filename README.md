# gradle-test-commit-revert-plugin

A small gradle plugin to implement the [Test-Commit-Revert](https://medium.com/@kentbeck_7670/test-commit-revert-870bbd756864) workflow.

## Usage

Include the plugin in your `build.gradle`

```groovy
plugins {
  id "uk.co.probablyfine.gradle.test-commit-revert" version "0.3.0"
}
```

By default, the commit command is `git commit --all --message TCR` and the revert command is `git reset --hard HEAD`.

You can override these in your `build.gradle` if you, say, use Mercurial as your VCS.

```groovy
testCommitRevert {
  commitCommand = 'hg commit -A -m TCR'
  revertCommand = 'hg update --clean'
}
```