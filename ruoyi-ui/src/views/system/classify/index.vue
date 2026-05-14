<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料类型编码" prop="materialClassifyCode">
        <el-input
          v-model="queryParams.materialClassifyCode"
          placeholder="请输入物料类型编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料类型名称" prop="materialClassifyName">
        <el-input
          v-model="queryParams.materialClassifyName"
          placeholder="请输入物料类型名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父类型ID" prop="parentClassifyId">
        <el-input
          v-model="queryParams.parentClassifyId"
          placeholder="请输入父类型ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所有层级父节点ID，用逗号分隔" prop="ancestors">
        <el-input
          v-model="queryParams.ancestors"
          placeholder="请输入所有层级父节点ID，用逗号分隔"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="显示顺序" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入显示顺序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="enable">
        <el-input
          v-model="queryParams.enable"
          placeholder="请输入是否启用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="MES同步id" prop="mesSyncId">
        <el-input
          v-model="queryParams.mesSyncId"
          placeholder="请输入MES同步id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:classify:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:classify:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:classify:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:classify:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classifyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="物料类型编码" align="center" prop="materialClassifyCode" />
      <el-table-column label="物料类型名称" align="center" prop="materialClassifyName" />
      <el-table-column label="父类型ID" align="center" prop="parentClassifyId" />
      <el-table-column label="所有层级父节点ID，用逗号分隔" align="center" prop="ancestors" />
      <el-table-column label="所有层级父节点名称用/分隔" align="center" prop="ancestorsName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="是否启用" align="center" prop="enable" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="MES同步id" align="center" prop="mesSyncId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:classify:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:classify:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改物料分类（支持多级分类）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="物料类型编码" prop="materialClassifyCode">
              <el-input v-model="form.materialClassifyCode" placeholder="请输入物料类型编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料类型名称" prop="materialClassifyName">
              <el-input v-model="form.materialClassifyName" placeholder="请输入物料类型名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="父类型ID" prop="parentClassifyId">
              <el-input v-model="form.parentClassifyId" placeholder="请输入父类型ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所有层级父节点名称用/分隔" prop="ancestorsName">
              <el-input v-model="form.ancestorsName" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="显示顺序" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="请输入显示顺序" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否启用" prop="enable">
              <el-input v-model="form.enable" placeholder="请输入是否启用" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="MES同步id" prop="mesSyncId">
              <el-input v-model="form.mesSyncId" placeholder="请输入MES同步id" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClassify, getClassify, delClassify, addClassify, updateClassify } from "@/api/system/classify"

export default {
  name: "Classify",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物料分类（支持多级分类）表格数据
      classifyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialClassifyCode: null,
        materialClassifyName: null,
        parentClassifyId: null,
        ancestors: null,
        ancestorsName: null,
        orderNum: null,
        enable: null,
        mesSyncId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialClassifyCode: [
          { required: true, message: "物料类型编码不能为空", trigger: "blur" }
        ],
        materialClassifyName: [
          { required: true, message: "物料类型名称不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物料分类（支持多级分类）列表 */
    getList() {
      this.loading = true
      listClassify(this.queryParams).then(response => {
        this.classifyList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        materialClassifyCode: null,
        materialClassifyName: null,
        parentClassifyId: null,
        ancestors: null,
        ancestorsName: null,
        orderNum: null,
        enable: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        mesSyncId: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加物料分类（支持多级分类）"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getClassify(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改物料分类（支持多级分类）"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClassify(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addClassify(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除物料分类（支持多级分类）编号为"' + ids + '"的数据项？').then(function() {
        return delClassify(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/classify/export', {
        ...this.queryParams
      }, `classify_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
