<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="物料编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料类型名称" prop="itemTypeName">
        <el-input
          v-model="queryParams.itemTypeName"
          placeholder="请输入物料类型名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格型号" prop="specification">
        <el-input
          v-model="queryParams.specification"
          placeholder="请输入规格型号"
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
          v-hasPermi="['system:item:add']"
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
          v-hasPermi="['system:item:edit']"
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
          v-hasPermi="['system:item:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:item:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="itemCode" />
      <el-table-column label="物料名称" align="center" prop="itemName" />
      <el-table-column label="物料类型编码" align="center" prop="itemTypeCode" />
      <el-table-column label="物料类型名称" align="center" prop="itemTypeName" />
      <el-table-column label="物料版本号" align="center" prop="itemVersion" />
      <el-table-column label="物料分类名称" align="center" prop="materialClassifyName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="是否启用" align="center" prop="enable" />
      <el-table-column label="发布状态" align="center" prop="publishStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:item:remove']"
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

    <!-- 添加或修改物料管理（仅存储激活版本）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="物料编码" prop="itemCode">
              <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料名称" prop="itemName">
              <el-input v-model="form.itemName" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料类型ID" prop="itemTypeId">
              <el-input v-model="form.itemTypeId" placeholder="请输入物料类型ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料类型编码" prop="itemTypeCode">
              <el-input v-model="form.itemTypeCode" placeholder="请输入物料类型编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料类型名称" prop="itemTypeName">
              <el-input v-model="form.itemTypeName" placeholder="请输入物料类型名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料版本号" prop="itemVersion">
              <el-input v-model="form.itemVersion" placeholder="请输入物料版本号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料分类ids" prop="materialClassifyIds">
              <el-input v-model="form.materialClassifyIds" placeholder="请输入物料分类ids" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料分类名称" prop="materialClassifyName">
              <el-input v-model="form.materialClassifyName" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" placeholder="请输入规格型号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="单位ID" prop="unitId">
              <el-input v-model="form.unitId" placeholder="请输入单位ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="单位名称" prop="unitName">
              <el-input v-model="form.unitName" placeholder="请输入单位名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否启用" prop="enable">
              <el-input v-model="form.enable" placeholder="请输入是否启用" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="供应商ID" prop="vendorId">
              <el-input v-model="form.vendorId" placeholder="请输入供应商ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="供应商名称" prop="vendorName">
              <el-input v-model="form.vendorName" placeholder="请输入供应商名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="材质" prop="material">
              <el-input v-model="form.material" placeholder="请输入材质" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="颜色" prop="color">
              <el-input v-model="form.color" placeholder="请输入颜色" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="重量" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入重量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述" prop="Info">
              <el-input v-model="form.Info" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="技术参数数组" prop="parametersValues">
              <el-input v-model="form.parametersValues" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图片URL" prop="url">
              <el-input v-model="form.url" placeholder="请输入图片URL" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否最新版本" prop="latestVersion">
              <el-input v-model="form.latestVersion" placeholder="请输入是否最新版本" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remake">
              <el-input v-model="form.remake" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请流程" prop="flowKey">
              <el-input v-model="form.flowKey" placeholder="请输入申请流程" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="流程实例ID" prop="flowInsId">
              <el-input v-model="form.flowInsId" placeholder="请输入流程实例ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="能否作为Bom的父件" prop="usedAsParent">
              <el-input v-model="form.usedAsParent" placeholder="请输入能否作为Bom的父件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="能否进行变更" prop="allowChange">
              <el-input v-model="form.allowChange" placeholder="请输入能否进行变更" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料同步UUID" prop="itemUuid">
              <el-input v-model="form.itemUuid" placeholder="请输入物料同步UUID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料同步MESID" prop="mesSyncId">
              <el-input v-model="form.mesSyncId" placeholder="请输入物料同步MESID" />
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
import { listItem, getItem, delItem, addItem, updateItem } from "@/api/system/item"

export default {
  name: "Item",
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
      // 物料管理（仅存储激活版本）表格数据
      itemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: null,
        itemName: null,
        itemTypeId: null,
        itemTypeCode: null,
        itemTypeName: null,
        itemVersion: null,
        materialClassifyIds: null,
        materialClassifyName: null,
        specification: null,
        unitId: null,
        unitName: null,
        status: null,
        enable: null,
        vendorId: null,
        vendorName: null,
        material: null,
        color: null,
        weight: null,
        Info: null,
        parametersValues: null,
        url: null,
        latestVersion: null,
        publishStatus: null,
        remake: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        usedAsParent: null,
        allowChange: null,
        itemUuid: null,
        mesSyncId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        itemName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        itemTypeId: [
          { required: true, message: "物料类型ID不能为空", trigger: "blur" }
        ],
        itemTypeCode: [
          { required: true, message: "物料类型编码不能为空", trigger: "blur" }
        ],
        itemTypeName: [
          { required: true, message: "物料类型名称不能为空", trigger: "blur" }
        ],
        itemVersion: [
          { required: true, message: "物料版本号不能为空", trigger: "blur" }
        ],
        materialClassifyIds: [
          { required: true, message: "物料分类ids不能为空", trigger: "blur" }
        ],
        materialClassifyName: [
          { required: true, message: "物料分类名称不能为空", trigger: "blur" }
        ],
        unitName: [
          { required: true, message: "单位名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        enable: [
          { required: true, message: "是否启用不能为空", trigger: "blur" }
        ],
        latestVersion: [
          { required: true, message: "是否最新版本不能为空", trigger: "blur" }
        ],
        publishStatus: [
          { required: true, message: "发布状态不能为空", trigger: "change" }
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
    /** 查询物料管理（仅存储激活版本）列表 */
    getList() {
      this.loading = true
      listItem(this.queryParams).then(response => {
        this.itemList = response.rows
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
        itemCode: null,
        itemName: null,
        itemTypeId: null,
        itemTypeCode: null,
        itemTypeName: null,
        itemVersion: null,
        materialClassifyIds: null,
        materialClassifyName: null,
        specification: null,
        unitId: null,
        unitName: null,
        status: null,
        enable: null,
        vendorId: null,
        vendorName: null,
        material: null,
        color: null,
        weight: null,
        Info: null,
        parametersValues: null,
        url: null,
        latestVersion: null,
        publishStatus: null,
        remake: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        usedAsParent: null,
        allowChange: null,
        itemUuid: null,
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
      this.title = "添加物料管理（仅存储激活版本）"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改物料管理（仅存储激活版本）"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addItem(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除物料管理（仅存储激活版本）编号为"' + ids + '"的数据项？').then(function() {
        return delItem(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
