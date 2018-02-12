/**
 * Created by Wonpang New on 2016/9/10.
 *
 * app service
 */

define(['app', 'constants'], function (app, constants) {
    app.service(
        'pipelineDataService',
        [
            '$http',
            '$q',
            PipelineService
        ]
    );

    function PipelineService($http, $q) {
        var self = this;

        // 搜索模块名
        self.getSearchModules = function (searchVal, username) {
            return $http.get(constants.api('/pipeline/module/query?limit=70&q=' + searchVal + '&owner=' + username))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得主干流水线
         * @param username
         * @param module
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getTrunkPipelines = function (username, module, pipelineBuildId) {
            return $http.get(constants.api('pipeline/v1/pipelineBuilds?username=' + username + '&module=' + module
                    + '&pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得单个分支流水线记录
         * @param username
         * @param module
         * @param branchName
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getBranchPipelines = function (username, module, branchName, pipelineBuildId) {
            return $http.get(constants.api('pipeline/v1/branch/pipelineBuilds?username=' + username + '&module=' + module
                    + '&branchName=' + branchName + '&pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得总体分支流水线记录
         * @param username
         * @param module
         * @param branchId
         * @returns {*}
         */
        self.getBranchesPipelines = function (username, module, branchId) {
            return $http.get(constants.api('pipeline/v1/branches/pipelineBuilds?username=' + username + '&module=' + module
                    + '&branchId=' + branchId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得编译详细信息
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getCompileBuildDetail = function (pipelineBuildId) {
            return $http.get(constants.api('compileBuild/v1/detail?pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获得发布详细信息
         * @param pipelineBuildId
         * @returns {*}
         */
        self.getReleaseDetail = function (pipelineBuildId) {
            return $http.get(constants.api('release/v1/detail?pipelineBuildId=' + pipelineBuildId))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 重新编译，不产生新的流水线
         * @param username
         * @param module
         * @param compileBuildId
         * @returns {*}
         */
        self.doRebuild = function (username, module, compileBuildId) {
            return $http.get(constants.api('compileBuild/v1/rebuild?compileBuildId=' + compileBuildId
                    + '&username=' + username + '&module=' + module))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 发布操作
         * @param releaseParams
         * @returns {*}
         */
        self.doRelease = function (releaseParams) {
            return $http.post(constants.api('release/doRelease'), releaseParams)
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获取推荐的三位版本
         * @param moduleId
         * @param branchName
         * @returns {*}
         */
        self.getThreeVersion = function (moduleId, branchName) {
            return $http.get(constants.api('release/v1/threeVersion?moduleId=' + moduleId + '&branchName=' + branchName))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 添加Jenkins配置
         * @param jenkinsConf
         * @returns {*}
         */
        self.addJenkinsConf = function (jenkinsConf) {
            return $http.post(constants.api('pipeline/jenkins/server'), jenkinsConf)
                .then(function (data) {
                    return data.data;
                });
        };
        /**
         * 获取发布记录
         * @param module
         * @param releaseId
         * @returns {*}
         */
        self.getReleaseHistory = function (module, releaseId) {
            return $http.get(constants.api('pipeline/release/' + module))
                .then(function (data) {
                    return data.data;
                });
        };

        /**
         * 获取Jenkins配置信息
         * @returns {*}
         */
        self.getJenkinsConfs = function(){
            return $http.get(constants.api('pipeline/jenkins/server/all'))
                .then(function (data) {
                    return data.data;
                });
        };

        self.deleteJenkinsConf = function(id){
            return $http.delete(constants.api("pipeline/jenkins/server/"+id))
                .then(function (data) {
                    return data.data;
            })
        };

        /**
         * 增加新的模块
         * @param username
         * @param repository
         * @returns {*}
         */
        self.addModule = function (username, repository) {
            return $http.get(constants.api('github/addModule?owner=' + username + '&repository=' + repository))
                .then(function (data) {
                    return data.data;
                });
        };

        self.getLoginUserInfo = function () {
            return $http.get(constants.api("pipeline/user/current")).then(function (data) {
                return data.data;
            })
        };
        /**
         * 分支页面检索用，获得前50个常用分支
         * @param username
         * @param module
         * @returns {*}
         */
        self.getBranches = function (username, module) {
            return $http.get(constants.api('branch/getBranches?username=' + username + '&module=' + module))
                .then(function (data) {
                    return data.data;
                });
        }
    }
});
